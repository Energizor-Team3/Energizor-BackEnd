package com.energizor.restapi.reservation.entity;


import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "reservation_time")
@AllArgsConstructor
@Getter
public class ReservationTime {
    @Id
    @Column(name = "reservation_time_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationTimeCode;

    @Column(name = "reservation_code")
    private int reservationCode;

    @Column(name = "meet_time")
    private int meetTime;


    public ReservationTime() {
    }


    public ReservationTime reservationTimeCode(int reservationTimeCode){
        this.reservationTimeCode = reservationTimeCode;
        return this;
    }
    public ReservationTime reservationCode(int reservationCode){
        this.reservationCode = reservationCode;
        return this;
    }

    public ReservationTime meetTime(int meetTime){
        this.meetTime = meetTime;
        return this;
    }

    public ReservationTime build() {
        return new ReservationTime(reservationTimeCode, reservationCode,  meetTime);
    }



}