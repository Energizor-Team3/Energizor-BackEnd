package com.energizor.restapi.users.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDTO> selectMyUserInfo(@PathVariable String userId){

        log.info("[MemberController]  selectMyMemberInfo   Start =============== ");
        log.info("[MemberController]  selectMyMemberInfo   {} ====== ", userId);

        log.info("[MemberController]  selectMyMemberInfo   End ================= ");
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK, "조회 성공", userService.selectMyInfo(userId)));
    }
}
