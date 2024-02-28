package com.energizor.restapi.project.service;


import com.energizor.restapi.project.dto.ProjectAndParticipantDTO;
import com.energizor.restapi.project.dto.ProjectDTO;
import com.energizor.restapi.project.dto.ProjectParticipantDTO;
import com.energizor.restapi.project.dto.TaskDTO;
import com.energizor.restapi.project.entity.Project;
import com.energizor.restapi.project.entity.ProjectParticipant;
import com.energizor.restapi.project.entity.Task;
import com.energizor.restapi.project.repository.ProjectParticipantRepository;
import com.energizor.restapi.project.repository.ProjectRepository;
import com.energizor.restapi.project.repository.TaskRepository;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    private final UserRepository userRepository;
    private final ProjectParticipantRepository projectParticipantRepository;

    public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper, TaskRepository taskRepository, UserRepository userRepository, ProjectParticipantRepository projectParticipantRepository) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;

        this.projectParticipantRepository = projectParticipantRepository;
    }

    public List<ProjectDTO> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }
    public ProjectDTO findProject(int proNo) {
        Project project = projectRepository.findById(proNo).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);

        List<ProjectParticipant> participants = projectParticipantRepository.findByProNo(proNo); // 프로젝트 번호로 참여자 목록 조회
        List<ProjectParticipantDTO> participantDTOs = participants.stream().map(participant -> {
            ProjectParticipantDTO dto = modelMapper.map(participant, ProjectParticipantDTO.class);
            dto.setUserName(participant.getUser().getUserName()); // User의 이름 설정
            return dto;
        }).collect(Collectors.toList());

        projectDTO.setParticipants(participantDTOs); // 참여자 정보 설정
        return projectDTO;
    }

    public void deleteProject(int proNo) {

        List<Task> tasks = taskRepository.findByProParNo_ProNo(proNo);
        taskRepository.deleteAll(tasks);


        List<ProjectParticipant> participants = projectParticipantRepository.findByProNo(proNo);
        projectParticipantRepository.deleteAll(participants);


        projectRepository.deleteById(proNo);
    }

    @Transactional
    public TaskDTO addTask(String taskContent, int proParNo) {
        ProjectParticipant participant = projectParticipantRepository.findById(proParNo)
                .orElseThrow(() -> new EntityNotFoundException("Project Participant not found"));

        Task newTask = new Task();
        newTask.setTaskContent(taskContent);
        newTask.setTaskStatus("T"); // 기본 상태 설정
        newTask.setProParNo(participant); // 연관 관계 설정

        taskRepository.save(newTask);

        // TaskDTO 변환 로직
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskNo(newTask.getTaskNo()); // ID 설정, 저장 후 생성된 ID
        taskDTO.setTaskContent(newTask.getTaskContent());
        taskDTO.setTaskStatus(newTask.getTaskStatus());
        taskDTO.setProParNo(newTask.getProParNo().getProParNo()); // ProjectParticipant의 ID 설정


        return taskDTO;
    }

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
    }

    public void deleteTask(int taskNo) {
        taskRepository.deleteById(taskNo);
    }


    @Transactional
    public ProjectDTO addProjectAndParticipants(ProjectAndParticipantDTO projectAndParticipantDTO) {
        // DTO로부터 Project 엔티티 객체 생성
        Project project = new Project();
        project.setProTitle(projectAndParticipantDTO.getProTitle());
        project.setProContent(projectAndParticipantDTO.getProContent());
        project.setProStartDate(projectAndParticipantDTO.getProStartDate());
        project.setProEndDate(projectAndParticipantDTO.getProEndDate());
        project.setProStatus(projectAndParticipantDTO.getProStatus());

        // 프로젝트 저장
        Project savedProject = projectRepository.save(project);

        // 참여자 등록
        List<ProjectParticipant> participants = projectAndParticipantDTO.getUserCodes().stream()
                .map(userCode -> {
                    ProjectParticipant participant = new ProjectParticipant();
                    participant.setProNo(savedProject.getProNo());
                    participant.setUserCode(userCode);
                    return participant;
                }).collect(Collectors.toList());

        // 참여자 정보 저장
        projectParticipantRepository.saveAll(participants);

        // 저장된 프로젝트를 DTO로 변환하여 반환
        return modelMapper.map(savedProject, ProjectDTO.class);
    }

    @Transactional
    public TaskDTO updateTaskStatus(int taskNo, String taskStatus) {
        Task task = taskRepository.findById(taskNo)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        task.setTaskStatus(taskStatus);
        taskRepository.save(task);

        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);


        return taskDTO;
    }






}
