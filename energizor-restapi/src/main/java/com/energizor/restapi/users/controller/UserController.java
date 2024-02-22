package com.energizor.restapi.users.controller;
import com.energizor.restapi.common.Criteria;
import com.energizor.restapi.common.PageDTO;
import com.energizor.restapi.common.PagingResponseDTO;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.ChangePasswordRequest;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인사 관리 API")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "인사관리 - 전체 직원 조회", description = "관리자 권한을 가진 인사담당자가 전체 직원을 조회할 수 있습니다.")
    @GetMapping("/users-management")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> selectUserListWithPagingForAdmin(@RequestParam(name="offset", defaultValue="1") String offset, @AuthenticationPrincipal UserDTO principal) {
        log.info("[UserController] selectUserListWithPagingForAdmin : " + offset);
        System.out.println("principal ==-=-=-=-=-=-=- " + principal);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        Page<UserDTO> userDTOPage = userService.selectUserListWithPagingForAdmin(cri);
        pagingResponseDTO.setData(userDTOPage);
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) userDTOPage.getTotalElements()));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "직원 전체 조회 성공", pagingResponseDTO));
    }

    @Operation(summary = "직원 상세정보 조회", description = "관리자 권한을 가진 인사담당자가 개별 직원 정보를 조회할 수 있습니다.")
    // 바로 수정으로 넘어갈거면 삭제 !!!!!!!!!!!!!!!!!!!!
    @GetMapping("/users-management/{userCode}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> selectUserDetailForAdmin(@PathVariable int userCode, @AuthenticationPrincipal UserDTO principal) {

        System.out.println("principal =============>>>>>>>>>> " + principal);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "직원 상세정보 조회 성공",  userService.selectUserDetailForAdmin(userCode)));
    }

    @Operation(summary = "직원 정보 수정", description = "관리자 권한을 가진 인사담당자가 직원 정보를 수정할 수 있습니다.")
    @PutMapping(value = "/user-update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> updateUserForAdmin(@RequestBody UserDTO userDTO, @AuthenticationPrincipal UserDTO principal) {

        System.out.println("principal =============>>>>>>>>>> " + principal);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "직원 정보 수정 성공", userService.updateUserForAdmin(userDTO, principal)));
    }

    @Operation(summary = "내 정보 조회", description = "내 정보를 조회할 수 있습니다.")
    @GetMapping("/mypage")
    public ResponseEntity<ResponseDTO> selectMyUserInfo(@AuthenticationPrincipal UserDTO principal){

        log.info("[UserController]  selectMyUserInfo   Start =============== ");
        System.out.println("principal =============>>>>>>>>>> " + principal);

        log.info("[UserController]  selectMyUserInfo   End ================= ");
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK, "조회 성공", userService.selectMyInfo(principal.getUserId())));
    }

    @Operation(summary = "비밀번호 변경", description = "현재 비밀번호, 변경할 비밀번호, 재입력한 비밀번호를 받아서 비밀번호를 변경합니다.")
    @PutMapping(value = "/change-password")
    @PreAuthorize("hasRole('ROLE_USER')") // Assuming only authenticated users can change their password
    public ResponseEntity<ResponseDTO> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest,
            @AuthenticationPrincipal UserDTO principal) {

        System.out.println("principal =============>>>>>>>>>> " + principal);
        System.out.println("changePasswordRequest =============>>>>>>>>>> " + changePasswordRequest);

        // Check if the new password and confirm password match
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "새로운 비밀번호와 확인 비밀번호가 일치하지 않습니다.", null));
        }

        // Check if the current password is correct
        if (!userService.isCorrectPassword(principal.getUserCode(), changePasswordRequest.getCurrentPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(HttpStatus.UNAUTHORIZED, "현재 비밀번호가 일치하지 않습니다.", null));
        }

        // Change the password
        userService.changePassword(principal.getUserCode(), changePasswordRequest.getNewPassword());

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK, "비밀번호 변경 성공", null));
    }


}
