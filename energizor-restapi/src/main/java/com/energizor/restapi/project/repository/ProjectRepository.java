package com.energizor.restapi.project.repository;

import com.energizor.restapi.calendar.entity.Calendar;
import com.energizor.restapi.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
