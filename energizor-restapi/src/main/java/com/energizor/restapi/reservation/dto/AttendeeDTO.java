package com.energizor.restapi.reservation.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendeeDTO {
    private int attCode;
    private int reservationCode;
    private int userCode;

}
