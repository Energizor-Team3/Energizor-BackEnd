package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    Optional<Schedule> findById(Integer schNo);
    @Query("SELECT s, c.calColor FROM Schedule s " +
            "INNER JOIN CalendarParticipant cp ON s.calNo = cp.calParticipant.calNo " +
            "INNER JOIN Calendar c ON s.calNo = c.calNo " +
            "WHERE cp.calParticipant.userCode = :userCode")
    List<Object[]> findScheduleAndColorByUserCode(@Param("userCode") int userCode);

    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.calNo = :calNo")
    void deleteByCalNo(@Param("calNo") int calNo);

}
//    @Query("SELECT s, c.calColor FROM Schedule s " +
//            "INNER JOIN CalendarParticipant cp ON s.calNo = cp.calParticipant.calNo " +
//            "INNER JOIN Calendar c ON s.calNo = c.calNo " +
//            "WHERE cp.calParticipant.userCode = :userCode")
//    List<Schedule> findScheduleByUserCode(@Param("userCode") int userCode);