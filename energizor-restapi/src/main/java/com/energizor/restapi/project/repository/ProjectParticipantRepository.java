package com.energizor.restapi.project.repository;

import com.energizor.restapi.project.entity.ProjectParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectParticipantRepository extends JpaRepository<ProjectParticipant, Integer> {
    List<ProjectParticipant> findByProNo(int proNo); // 프로젝트 번호로 참여자 목록 조회
}
