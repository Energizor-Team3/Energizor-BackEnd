package com.energizor.restapi.auth.filter;

import com.energizor.restapi.common.AuthConstants;
import com.energizor.restapi.users.dto.AuthorityDTO;
import com.energizor.restapi.users.dto.DayOffDTO;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.dto.UserRoleDTO;
import com.energizor.restapi.util.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*
         * 권한이 필요없는 리소스
         * */

        List<String> roleLeessList = Arrays.asList(
                "/auth/login", "/auth/searchpwd",
                "/swagger-ui/(.*)",        //swagger 설정
                "/swagger-ui/index.html",  //swagger 설정
                "/v3/api-docs",              //swagger 설정
                "/v3/api-docs/(.*)",         //swagger 설정
                "/swagger-resources",        //swagger 설정
                "/swagger-resources/(.*)"    //swagger 설정
        );

        if(roleLeessList.stream().anyMatch(uri -> roleLeessList.stream().anyMatch(pattern -> Pattern.matches(pattern, request.getRequestURI())))){
            chain.doFilter(request,response);
            return;
        }


        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        try {
            if(header != null && !header.equalsIgnoreCase("")){
                String token = TokenUtils.splitHeader(header);

                if(TokenUtils.isValidToken(token)){
                    Claims claims = TokenUtils.getClaimsFromToken(token);
                    System.out.println("claims ===============> " + claims);

                    UserDTO authentication = new UserDTO();
                    authentication.setUserCode((Integer) claims.get("userCode"));
                    authentication.setUserId(claims.get("userId").toString());
                    authentication.setUserName(claims.get("userName").toString());
                    authentication.setUserRank(claims.get("userRank").toString());
                    authentication.setEmail(claims.get("email").toString());
                    System.out.println("claims userRole ==================== " + claims.get("userRole")); // [{userCode=2, authCode=1, authority={authCode=1, authName=ROLE_USER}}]
                    System.out.println("claims offCode ==================== " + claims.get("dayoff"));
                    authentication.setUserPw(claims.get("userPw").toString());
                    System.out.println("claims offCode ==================== " + claims.get("userPw"));

                    // List<UserRoleDTO> 설정
                    List<UserRoleDTO> userRoles = mapToUserRoleList(claims.get("userRole"));
                    System.out.println("userRoles =-======================>>>> " + userRoles);
                    authentication.setUserRole(userRoles);

                    AbstractAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(authentication, token, authentication.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request,response);
                }else{
                    throw new RuntimeException("토큰이 유효하지 않습니다.");
                }
            }else{
                throw new RuntimeException("토큰이 존재하지 않습니다.");
            }
        }catch (Exception e){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonresponseWrapper(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    @SuppressWarnings("unchecked")
    private List<UserRoleDTO> mapToUserRoleList(Object userRoleObject) {
        List<UserRoleDTO> userRoles = new ArrayList<>();
        if (userRoleObject instanceof List<?>) {
            for (Map<String, Object> roleMap : (List<Map<String, Object>>) userRoleObject) {
                UserRoleDTO userRole = new UserRoleDTO();
                userRole.setUserCode((Integer) roleMap.get("userCode"));
                userRole.setAuthCode((Integer) roleMap.get("authCode"));

                Object authorityObject = roleMap.get("authority");
                userRole.setAuthority(AuthorityDTO.fromLinkedHashMap((LinkedHashMap<String, Object>) authorityObject));

                userRoles.add(userRole);
            }
            System.out.println("userRoles =========== " + userRoles);
        }
        return userRoles;
    }


    /**
     * 토큰 관련된 Exception 발생 시 예외 응답
     * */
    private JSONObject jsonresponseWrapper(Exception e) {
        String resultMsg = "";
        if (e instanceof ExpiredJwtException) {
            resultMsg = "Token Expired";
        } else if (e instanceof SignatureException) {
            resultMsg = "TOKEN SignatureException Login";
        }
        // JWT 토큰내에서 오류 발생 시
        else if (e instanceof JwtException) {
            resultMsg = "TOKEN Parsing JwtException";
        }
        // 이외 JTW 토큰내에서 오류 발생
        else {
            resultMsg = "OTHER TOKEN ERROR";
        }

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401);
        jsonMap.put("message", resultMsg);
        jsonMap.put("reason", e.getMessage());
        JSONObject jsonObject = new JSONObject(jsonMap);
        return jsonObject;
    }
}
