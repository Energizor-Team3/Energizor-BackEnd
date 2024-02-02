package com.energizor.restapi.approval.repository;


import com.energizor.restapi.approval.entity.Education;
import com.energizor.restapi.approval.entity.GeneralDraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Integer> {
}
