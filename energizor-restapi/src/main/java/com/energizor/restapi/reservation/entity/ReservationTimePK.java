package com.energizor.restapi.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Embeddable
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ReservationTimePK implements Serializable {

    @Column(name = "reservation_code")
    private int reservationCode;

    @Column(name = "meet_time")
    private Time meetTime;

    public ReservationTimePK() {}

    public ReservationTimePK reservationCode(int reservationCode){
        this.reservationCode = reservationCode;
        return this;
    }

    public ReservationTimePK meetTime(Time meetTime){
        this.meetTime = meetTime;
        return this;
    }
}
