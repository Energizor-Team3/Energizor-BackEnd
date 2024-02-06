package com.energizor.restapi.group.repository;
import com.energizor.restapi.group.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
}
