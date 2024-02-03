package com.energizor.restapi.reservation.dto;

import com.energizor.restapi.reservation.entity.Reservation;
import com.energizor.restapi.users.entity.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendeeDTO {
    private int attCode;
    private Reservation reservationCode;
    private User userCode;

}
