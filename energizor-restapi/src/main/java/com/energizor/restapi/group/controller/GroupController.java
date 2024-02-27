package com.energizor.restapi.group.controller;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.group.dto.DeptGroupDTO;
import com.energizor.restapi.group.dto.TeamGroupDTO;
import com.energizor.restapi.group.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Group Controller 조직도 전체조회 및 부서,팀 추가/수정/삭제(유저CUD제외)")
@RestController
@RequestMapping("/group")
@Slf4j
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    /* 전체조회 */

    @GetMapping("/groupList")
    public ResponseEntity<ResponseDTO> selectAllGroup() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "모든 그룹 조회 성공", groupService.selectAllGroupList()));
    }


    /* 조회 */

    @GetMapping("/dept/{deptCode}")
    public ResponseEntity<ResponseDTO> selectDept(@PathVariable int deptCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조직도 부서,팀 조회 성공" , groupService.selectDeptAndTeam(deptCode)));
    }

    @GetMapping("/team/{teamCode}")
    public ResponseEntity<ResponseDTO> selectTeam(@PathVariable int teamCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조직도 팀,유저 조회 성공" , groupService.selectTeamAndUsers(teamCode)));
    }

    @GetMapping("/user/{userCode}")
    public ResponseEntity<ResponseDTO> selectUser(@PathVariable int userCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조직도 유저 조회 성공" , groupService.selectUsers(userCode)));
    }

    /* 추가 */
    @Operation(summary = "부서 추가", description = "괸리자 권한을 가진 인사담당자가 부서추가를 할 수 있습니다")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/dept-insert")
    public ResponseEntity<ResponseDTO> insertDept(@RequestBody DeptGroupDTO deptGroupDTO) {

        System.out.println("부서추가값확인======="+ deptGroupDTO);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "부서 생성 성공" , groupService.insertDept(deptGroupDTO)));
    }

    @Operation(summary = "팀 추가", description = "괸리자 권한을 가진 인사담당자가 팀추가를 할 수 있습니다")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/team-insert")
    public ResponseEntity<ResponseDTO> insertTeam(@RequestBody TeamGroupDTO teamGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "팀 생성 성공" , groupService.insertTeam(teamGroupDTO)));
    }

    /* 수정 */

    @Operation(summary = "부서 수정", description = "괸리자 권한을 가진 인사담당자가 부서수정를 할 수 있습니다")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/dept-update")
    public ResponseEntity<ResponseDTO> updateDept(@RequestBody DeptGroupDTO deptGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "부서 수정 성공" , groupService.updateDept(deptGroupDTO)));
    }

    @Operation(summary = "팀 수정", description = "괸리자 권한을 가진 인사담당자가 팀수정를 할 수 있습니다")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/team-update")
    public ResponseEntity<ResponseDTO> updateTeam(@RequestBody TeamGroupDTO teamGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "팀 수정 성공" , groupService.updateTeam(teamGroupDTO)));
    }

    /* 삭제 */

    @Operation(summary = "부서 삭제", description = "괸리자 권한을 가진 인사담당자가 부서삭제를 할 수 있습니다")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/dept-delete")
    public ResponseEntity<ResponseDTO> deleteDept(@RequestBody DeptGroupDTO deptGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "부서 삭제 성공" , groupService.deleteDept(deptGroupDTO)));
    }

    @Operation(summary = "팀 삭제", description = "괸리자 권한을 가진 인사담당자가 팀삭제를 할 수 있습니다")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/team-delete")
    public ResponseEntity<ResponseDTO> deleteTeam(@RequestBody TeamGroupDTO teamGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "팀 삭제 성공" , groupService.deleteTeam(teamGroupDTO)));
    }

}
