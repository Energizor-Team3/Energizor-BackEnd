package com.energizor.restapi.auth.handler;

import com.energizor.restapi.exception.LoginFailedException;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService detailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication;

        System.out.println("loginToken = " + loginToken);
        System.out.println("----------------------"+loginToken.getName());
        String userId = loginToken.getName();   // 사번!!!
        String password = (String) loginToken.getCredentials();
        System.out.println("password = " + password);



        UserDTO user = (UserDTO) detailsService.loadUserByUsername(userId);
        System.out.println("user ===================== " + user);
        System.out.println(user.getUserPw());
        System.out.println("비밀번호 틀렸는지 확인 =================" + passwordEncoder.matches(password, user.getUserPw()));

        if(!passwordEncoder.matches(password, user.getUserPw())){
            throw new BadCredentialsException(password + "는 비밀번호가 아닙니다.");
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
