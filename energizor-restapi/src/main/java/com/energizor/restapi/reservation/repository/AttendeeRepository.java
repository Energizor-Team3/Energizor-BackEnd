package com.energizor.restapi.reservation.repository;

import com.energizor.restapi.reservation.entity.Attendee;
import com.energizor.restapi.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {


    List<Attendee> findByReservationReservationCode(int reservationCode);
}
