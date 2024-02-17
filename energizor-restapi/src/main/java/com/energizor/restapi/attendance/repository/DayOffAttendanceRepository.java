package com.energizor.restapi.attendance.repository;

import com.energizor.restapi.attendance.entity.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayOffAttendanceRepository extends JpaRepository<DayOff, Integer> {
}
