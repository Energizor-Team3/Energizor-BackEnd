package com.energizor.restapi.project.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping("/projects")
    public ResponseEntity<ResponseDTO> getAllCalendars() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "모든 프로젝트 목록 조회 성공", projectService.findAllProjects()));
    }

    @GetMapping("/{proNo}")
    public ResponseEntity<ResponseDTO> findProject(@PathVariable int proNo) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,  proNo+" 번 프로젝트 조회 성공",projectService.findProject(proNo)));
    }

    @GetMapping("/{proNo}/tasks")
    public ResponseEntity<ResponseDTO> findTasksByProjectNo(@PathVariable int proNo) {
        // 주어진 프로젝트 번호에 해당하는 모든 업무 정보와 사용자 이름을 조회합니다.
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "프로젝트 업무 목록 조회 성공", projectService.findTasksByProjectNo(proNo)));
    }

}
