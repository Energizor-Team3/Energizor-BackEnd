package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT s, c.calColor FROM Schedule s " +
            "INNER JOIN CalendarParticipant cp ON s.calNo = cp.calParticipant.calNo " +
            "INNER JOIN Calendar c ON s.calNo = c.calNo " +
            "WHERE cp.calParticipant.userCode = :userCode")
    List<Object[]> findScheduleAndColorByUserCode(@Param("userCode") int userCode);
//    @Query("SELECT s, c.calColor FROM Schedule s " +
//            "INNER JOIN CalendarParticipant cp ON s.calNo = cp.calParticipant.calNo " +
//            "INNER JOIN Calendar c ON s.calNo = c.calNo " +
//            "WHERE cp.calParticipant.userCode = :userCode")
//    List<Schedule> findScheduleByUserCode(@Param("userCode") int userCode);
}
