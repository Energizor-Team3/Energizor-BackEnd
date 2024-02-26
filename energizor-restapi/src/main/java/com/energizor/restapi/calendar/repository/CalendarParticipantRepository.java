package com.energizor.restapi.calendar.repository;

import com.energizor.restapi.calendar.entity.CalendarParticipant;
import com.energizor.restapi.calendar.entity.CalendarParticipantPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CalendarParticipantRepository extends JpaRepository<CalendarParticipant, CalendarParticipantPK> {
    List<CalendarParticipant> findByCalParticipant_UserCode(int userCode);

    @Modifying
    @Query("DELETE FROM CalendarParticipant cp WHERE cp.calParticipant.calNo = :calNo")
    void deleteByCalParticipant_CalNo(@Param("calNo") int calNo);

    @Query("SELECT u.userName FROM User u WHERE u.userCode IN (SELECT cp.calParticipant.userCode FROM CalendarParticipant cp WHERE cp.calParticipant.calNo = :calNo)")
    List<String> findParticipantNamesByCalNo(@Param("calNo") int calNo);



}
