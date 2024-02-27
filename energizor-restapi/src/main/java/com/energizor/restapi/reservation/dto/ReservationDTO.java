package com.energizor.restapi.reservation.dto;

import com.energizor.restapi.reservation.entity.Meet;
import com.energizor.restapi.reservation.entity.MeetingTime;
import com.energizor.restapi.users.dto.UserDTO;

import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ReservationDTO {

    private int reservationCode;
    private LocalDate reservationDate;
    private String reservationContent;
    private UserDTO userCode;
    private Meet meet;
    private List<com.energizor.restapi.reservation.dto.UserDTO> member;


    private String startTime;
    private String endTime;



    public ReservationDTO(int reservationCode) {
        this.reservationCode = reservationCode;
    }
}
