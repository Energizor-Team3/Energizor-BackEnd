package com.energizor.restapi.project.service;


import com.energizor.restapi.project.dto.ProjectDTO;
import com.energizor.restapi.project.dto.TaskDTO;
import com.energizor.restapi.project.entity.Project;
import com.energizor.restapi.project.entity.Task;
import com.energizor.restapi.project.repository.ProjectRepository;
import com.energizor.restapi.project.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    public List<ProjectDTO> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }
    public ProjectDTO findProject(int proNo){
        Project project = projectRepository.findById(proNo).get();
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
        return projectDTO;
    }
//
//    // 이 메소드는 주어진 프로젝트 번호에 해당하는 모든 업무를 조회합니다.
//    public List<TaskDTO> findTasksByProjectNo(int proNo) {
//        // 해당 프로젝트 번호에 해당하는 모든 업무를 조회합니다.
//        List<Task> tasks = taskRepository.findByProParNo_ProNo(proNo);
//
//        // 각 업무를 TaskDTO로 매핑하여 반환합니다.
//        return tasks.stream()
//                .map(task -> modelMapper.map(task, TaskDTO.class))
//                .collect(Collectors.toList());
//    }


    // 이 메소드는 주어진 프로젝트 번호에 해당하는 모든 업무를 조회합니다.
    public List<TaskDTO> findTasksByProjectNo(int proNo) {
        // 해당 프로젝트 번호에 해당하는 모든 업무를 조회합니다.
        List<Task> tasks = taskRepository.findByProParNo_ProNo(proNo);

        // 각 업무를 TaskDTO로 매핑하여 반환합니다.
        return tasks.stream()
                .map(task -> {
                    TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
                    // 사용자 이름도 함께 조회합니다.
                    taskDTO.setUserName(task.getProParNo().getUser().getUserName());
                    return taskDTO;
                })
                .collect(Collectors.toList());
    }}
