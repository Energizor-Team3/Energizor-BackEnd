package com.energizor.restapi.reservation.dto;

import com.energizor.restapi.reservation.entity.Meet;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
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
    private UserDTO user;
    private MeetDTO meet;



}
