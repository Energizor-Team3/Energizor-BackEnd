package com.energizor.restapi.users.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.MailDTO;
import com.energizor.restapi.users.dto.PasswordResetRequest;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.service.AuthService;
import com.energizor.restapi.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> signup(@RequestBody UserDTO userDTO, @AuthenticationPrincipal UserDTO principal) {
        System.out.println("principal =================================== " + principal);
        // 멤버의 기본 상태값 설정
        userDTO.setUserStatus("Y");
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.CREATED, "회원가입 성공", authService.signup(userDTO)));
    }

//    @GetMapping("/password")
//    public String findPassword(Model model) {
//        return "/auth/findPwForm";
//    }
//
//    @PostMapping("/password")
//    public String sendEmail(@RequestParam("email") String email) {
//        MailDTO mailDTO = authService.createMailAndChangePassword(email);
////        authService.mailSend(mailDTO);
//
//        return "auth/findPwForm";
//    }

    @PostMapping("/searchpwd")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody PasswordResetRequest request, @AuthenticationPrincipal UserDTO principal) {

        System.out.println("비번 변경 컨트롤러 시작=====================================");

        try {
            authService.sendSearchPwd((request.getUserId()), request.getEmail());
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "임시 비밀번호로 변경 성공", "변경 성공"));
        }  catch (UserPrincipalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다.", "User not found."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", "Internal server error."));
        }
    }



}
