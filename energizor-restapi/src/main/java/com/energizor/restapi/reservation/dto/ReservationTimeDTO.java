package com.energizor.restapi.reservation.dto;

import lombok.*;

import java.sql.Time;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationTimeDTO {
    private int reservationCode;
    private Time meetTime;
}
