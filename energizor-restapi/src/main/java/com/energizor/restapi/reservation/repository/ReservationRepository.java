package com.energizor.restapi.reservation.repository;

import com.energizor.restapi.reservation.entity.Attendee;
import com.energizor.restapi.reservation.entity.Reservation;
import com.energizor.restapi.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    Optional<Reservation> findByReservationCode(int reservationCode);


    List<Reservation> findByUserCode(User user);
}
