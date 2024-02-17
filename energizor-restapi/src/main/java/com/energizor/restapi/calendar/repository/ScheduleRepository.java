package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
