package com.energizor.restapi.util;

import com.energizor.restapi.users.dto.UserDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 토큰을 관리하기 위한 utils 모음 클래스
 *  yml -> jwt-key, jwt-time 설정이 필요하다.
 *  jwt lib 버전 "io.jsonwebtoken:jjwt:0.9.1" 사용
 * */
@Component
public class TokenUtils {

    private static String jwtSecretKey;
    private static Long tokenValidateTime;

    private static SecureRandom secureRandom;

    @Value("${jwt.key}")
    public void setJwtSecretKey(String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.time}")
    public void setTokenValidateTime(Long tokenValidateTime) {
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    /**
     * header의 token을 분리하는 메서드
     * @param header: Authrization의 header값을 가져온다.
     * @return token: Authrization의 token 부분을 반환한다.
     * */
    public static String splitHeader(String header){
        System.out.println("header = " + header);
        if(!header.equals("")){
            return header.split(" ")[1];
        }else{
            return null;
        }
    }

    /**
     * 유효한 토큰인지 확인하는 메서드
     * @param token : 토큰
     * @return boolean : 유효 여부
     * @throws ExpiredJwtException, {@link JwtException} {@link NullPointerException}
     * */
    public static boolean isValidToken(String token){

        try{
            Claims claims = getClaimsFromToken(token);
            return true;
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch (JwtException e){
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 토큰을 복호화 하는 메서드
     * @param token
     * @return Claims
     * */
    public static Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token).getBody();
    }


    /**
     * token을 생성하는 메서드
     * @param user 사용자객체
     * @return String - token
     * */
    public static String generateJwtToken(UserDTO user) {
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);


        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject(String.valueOf(user.getUserId()))
                .signWith(SignatureAlgorithm.HS256, createSignature())
                .setExpiration(expireTime);


        return builder.compact();
    }

    /**
     * token의 header를 설정하는 부분이다.
     * @return Map<String, Object> - header의 설정 정보
     * */
    private static Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();

        header.put("type", "jwt");
        header.put("alg", "HS256");
        header.put("date", System.currentTimeMillis());

        return header;
    }

    /**
     * 사용자 정보를 기반으로 클레임을 생성해주는 메서드
     *
     * @param user - 사용자 정보
     * @return Map<String, Object> - claims 정보
     * */
    private static Map<String, Object> createClaims(UserDTO user){
        Map<String, Object> claims = new HashMap<>();

        claims.put("userCode", user.getUserCode());
        claims.put("userId", user.getUserId());
        claims.put("userName", user.getUserName());
        claims.put("userPw", user.getUserPw());
        System.out.println("user.getUserPw() = " + user.getUserPw());
        claims.put("userRank", user.getUserRank());
        claims.put("userRole", user.getUserRole());
        claims.put("email", user.getEmail());
//        claims.put("dayoff", user.getDayoff());
        claims.put("teamDTO", user.getTeam());
        claims.put("profilePath", user.getProfilePath());


        return claims;
    }

    /**
     * JWT 서명을 발급해주는 메서드이다.
     *
     * @return key
     * */
    private static Key createSignature(){
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    /***
     * 토큰이나 임시 비밀번호를 위한 랜덤 문자열 생성 매서드이다.
     * @return 랜덤 문자열
     */
    public static String randomString() {
        byte[] randomBytes = new byte[8];
        try {
            secureRandom.nextBytes(randomBytes);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리 - 원하는 방식으로 처리하도록 수정 가능
        }
        System.out.println("randomBytes 출력 = " + randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
