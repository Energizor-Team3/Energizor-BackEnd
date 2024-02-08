package com.energizor.restapi.project.service;


import com.energizor.restapi.calendar.dto.CalendarDTO;
import com.energizor.restapi.calendar.entity.Calendar;
import com.energizor.restapi.project.dto.ProjectDTO;
import com.energizor.restapi.project.entity.Project;
import com.energizor.restapi.project.repository.ProjectRepository;
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

    public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
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

}
