package com.energizor.restapi.reservation.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendeeDTO {
    private int att_code;
    private int reservation_code;
    private int user_id;

}
