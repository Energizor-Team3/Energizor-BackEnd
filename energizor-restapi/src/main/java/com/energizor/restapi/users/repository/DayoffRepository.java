package com.energizor.restapi.users.repository;

import com.energizor.restapi.users.entity.Dayoff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayoffRepository extends JpaRepository<Dayoff, Integer> {
}
