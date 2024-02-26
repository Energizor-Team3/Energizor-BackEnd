package com.energizor.restapi.reservation.repository;

import com.energizor.restapi.reservation.entity.Reservation;
import com.energizor.restapi.reservation.entity.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationTimeRepository  extends JpaRepository<ReservationTime, Integer> {
}
