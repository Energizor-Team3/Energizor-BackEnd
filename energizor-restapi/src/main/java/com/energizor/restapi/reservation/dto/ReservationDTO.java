package com.energizor.restapi.reservation.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

    private int reservation_code;
    private Date reservation_date;
    private String reservation_content;
    private int user_code;
    private int meet_code;



}
