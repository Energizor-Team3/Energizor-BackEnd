package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findBycalType(String calType);


}