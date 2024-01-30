package com.energizor.restapi.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReservationTimePK implements Serializable {

    @Column(name = "reservation_code")
    private int reservation_code;

    @Column(name = "meet_time")
    private Time meet_time;

}
