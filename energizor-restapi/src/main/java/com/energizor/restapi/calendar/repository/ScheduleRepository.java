package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.Calendar;
import com.energizor.restapi.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findBycalType(String calType);
}