package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.CalendarParticipant;
import com.energizor.restapi.calendar.entity.CalendarParticipantPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarParticipantRepository extends JpaRepository<CalendarParticipant, CalendarParticipantPK> {
    List<CalendarParticipant> findByCalParticipant_UserCode(int userCode);
}
