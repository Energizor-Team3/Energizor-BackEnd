package com.energizor.restapi.users.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.MailDTO;
import com.energizor.restapi.users.dto.PasswordResetRequest;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.service.AuthService;
import com.energizor.restapi.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "직원 등록", description = "관리자 권한을 가진 인사담당자가 새로운 직원 등록을 할 수 있습니다.")
    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> signup(@RequestBody UserDTO userDTO, @AuthenticationPrincipal UserDTO principal) {
        System.out.println("principal =================================== " + principal);
        // 멤버의 기본 상태값 설정
        userDTO.setUserStatus("Y");
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.CREATED, "회원가입 성공", authService.signup(userDTO)));
    }


    @Operation(summary = "비밀번호 찾기", description = "비밀번호를 잊었을 때 찾을 수 있습니다.")
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
