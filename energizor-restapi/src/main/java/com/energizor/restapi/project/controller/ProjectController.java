package com.energizor.restapi.project.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag( name= "프로젝트 관련 스웨거 연동")
@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @Operation(summary = "전체 프로젝트 조회  ",description = " 전체 프로젝트 목록 조회 한다 ")
    @GetMapping("/projects")
    public ResponseEntity<ResponseDTO> getAllCalendars() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "모든 프로젝트 목록 조회 성공", projectService.findAllProjects()));
    }
    @Operation(summary = "하나의 프로젝트 정보 조회   ",description = " 하나의 프로젝트 정보 조회한다 ")
    @GetMapping("/{proNo}")
    public ResponseEntity<ResponseDTO> findProject(@PathVariable int proNo) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,  proNo+" 번 프로젝트 조회 성공",projectService.findProject(proNo)));
    }
    @Operation(summary = "한 프로젝트의 업무 조회    ",description = " 프로젝트별 업무를 조회한다  ")
    @GetMapping("/{proNo}/tasks")
    public ResponseEntity<ResponseDTO> findTasksByProjectNo(@PathVariable int proNo) {
        // 주어진 프로젝트 번호에 해당하는 모든 업무 정보와 사용자 이름을 조회합니다.
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "프로젝트 업무 목록 조회 성공", projectService.findTasksByProjectNo(proNo)));
    }

}
