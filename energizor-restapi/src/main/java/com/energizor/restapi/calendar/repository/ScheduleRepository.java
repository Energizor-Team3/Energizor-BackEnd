package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query(value =
            "SELECT " +
                    "a.sch_no, b.cal_no, c.user_code " +
                    "FROM schedule a " +
                    "inner join calendar b on a.cal_no = b.cal_no " +
                    "inner join cal_participant c on b.cal_no = c.cal_no" +
                    "where c.user_code = :userCode",
            nativeQuery = true)
    List<Schedule> findScheduleByUserCode(int userCode);
}
