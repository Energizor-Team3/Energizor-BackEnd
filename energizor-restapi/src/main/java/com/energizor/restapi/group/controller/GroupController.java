package com.energizor.restapi.group.controller;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.group.dto.DeptGroupDTO;
import com.energizor.restapi.group.dto.TeamGroupDTO;
import com.energizor.restapi.group.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@Slf4j
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
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
    @PostMapping("/dept-insert")
    public ResponseEntity<ResponseDTO> insertDept(@RequestBody DeptGroupDTO deptGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "부서 생성 성공" , groupService.insertDept(deptGroupDTO)));
    }

    @PostMapping("/team-insert")
    public ResponseEntity<ResponseDTO> insertTeam(@RequestBody TeamGroupDTO teamGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "팀 생성 성공" , groupService.insertTeam(teamGroupDTO)));
    }

    /* 수정 */

    @PostMapping("/dept-update")
    public ResponseEntity<ResponseDTO> updateDept(@RequestBody DeptGroupDTO deptGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "부서 수정 성공" , groupService.updateDept(deptGroupDTO)));
    }

    @PostMapping("/team-update")
    public ResponseEntity<ResponseDTO> updateTeam(@RequestBody TeamGroupDTO teamGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "팀 수정 성공" , groupService.updateTeam(teamGroupDTO)));
    }

    /* 삭제 */

    @PostMapping("/dept-delete")
    public ResponseEntity<ResponseDTO> deleteDept(@RequestBody DeptGroupDTO deptGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "부서 삭제 성공" , groupService.deleteDept(deptGroupDTO)));
    }

    @PostMapping("/team-delete")
    public ResponseEntity<ResponseDTO> deleteTeam(@RequestBody TeamGroupDTO teamGroupDTO) {

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "팀 삭제 성공" , groupService.deleteTeam(teamGroupDTO)));
    }

}
