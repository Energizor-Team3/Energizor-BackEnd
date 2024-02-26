package com.energizor.restapi.reservation.repository;

import com.energizor.restapi.reservation.entity.Meet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;

public interface MeetRepository extends JpaRepository<Meet, Integer> {


    Meet findByMeetCode(int meetCode);


}
