package com.energizor.restapi.reservation.repository;

import com.energizor.restapi.reservation.entity.MeetingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;

@Repository
public interface MeetingTimeRepository extends JpaRepository<MeetingTime, Long> {






    MeetingTime findByTime(String startTime);


    // 추가적인 메서드가 필요한 경우 여기에 선언합니다.
}
