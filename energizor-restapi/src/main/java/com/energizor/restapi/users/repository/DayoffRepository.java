package com.energizor.restapi.users.repository;

import com.energizor.restapi.users.entity.Dayoff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayoffRepository extends JpaRepository<Dayoff, Integer> {
    Optional<Dayoff> findByUser_UserCode(int userCode);
}
