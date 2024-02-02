package com.energizor.restapi.reservation.repository;

import com.energizor.restapi.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


}
