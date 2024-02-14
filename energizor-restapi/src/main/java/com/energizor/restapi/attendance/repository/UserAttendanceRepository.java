package com.energizor.restapi.attendance.repository;

import com.energizor.restapi.attendance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAttendanceRepository extends JpaRepository<User, Integer> {
}
