package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByCalendar_CalNo(int calNo);
}
