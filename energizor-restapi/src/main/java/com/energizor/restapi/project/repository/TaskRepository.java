package com.energizor.restapi.project.repository;

import com.energizor.restapi.project.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    // 주어진 프로젝트 번호에 해당하는 모든 업무를 조회합니다.
    List<Task> findByProParNo_ProNo(int proNo);
}
