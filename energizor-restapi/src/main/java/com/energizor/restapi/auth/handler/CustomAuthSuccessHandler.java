package com.energizor.restapi.auth.handler;

import com.energizor.restapi.common.AuthConstants;
import com.energizor.restapi.users.dto.TokenDTO;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.util.ConvertUtil;
import com.energizor.restapi.util.TokenUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDTO user  = ((UserDTO) authentication.getPrincipal());

        HashMap<String, Object> responseMap = new HashMap<>();
        JSONObject jsonValue = null;
        JSONObject jsonObject;
        if(user.getUserStatus().equals("N")){
            responseMap.put("userInfo", jsonValue);
            responseMap.put("status", 500);
            responseMap.put("message","휴면상태인 계정입니다.");
        }else{

            String token = TokenUtils.generateJwtToken(user);
            // tokenDTO response
            TokenDTO tokenDTO = TokenDTO.builder()
                                .userName(user.getUsername())  // 사번
                                .accessToken(token)
                                .grantType(AuthConstants.TOKEN_TYPE)
                                .build();

            jsonValue = (JSONObject) ConvertUtil.convertObjectToJsonObject(tokenDTO);

            responseMap.put("userInfo", jsonValue);
            responseMap.put("status", 200);
            responseMap.put("message", "Login Success");
        }

        jsonObject = new JSONObject(responseMap);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}
