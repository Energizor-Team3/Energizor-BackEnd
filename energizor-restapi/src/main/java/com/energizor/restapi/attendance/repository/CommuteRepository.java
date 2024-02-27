package com.energizor.restapi.attendance.repository;

import com.energizor.restapi.attendance.entity.Commute;
import com.energizor.restapi.attendance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommuteRepository extends JpaRepository<Commute, Integer> {
    List<Commute> findByUser(User user);
}