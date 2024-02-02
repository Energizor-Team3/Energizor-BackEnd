package com.energizor.restapi.reservation.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

    private int reservationCode;
    private Date reservationDate;
    private String reservationContent;
    private int userCode;
    private int meetCode;



}
