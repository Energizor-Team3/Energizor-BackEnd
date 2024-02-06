package com.energizor.restapi.group.controller;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.group.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/group")
@Slf4j
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

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

}
