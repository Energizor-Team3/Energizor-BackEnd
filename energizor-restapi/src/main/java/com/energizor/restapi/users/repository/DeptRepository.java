package com.energizor.restapi.users.repository;

import com.energizor.restapi.users.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
}
