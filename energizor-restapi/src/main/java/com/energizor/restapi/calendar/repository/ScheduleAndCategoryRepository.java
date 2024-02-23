package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.ScheduleAndCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleAndCategoryRepository extends JpaRepository<ScheduleAndCalendar, Integer> {
    List<ScheduleAndCalendar> findByCalendar_CalNo(int calNo);
}
