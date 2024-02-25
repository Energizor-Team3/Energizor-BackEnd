package com.energizor.restapi.reservation.dto;

import com.energizor.restapi.reservation.entity.Meet;
import com.energizor.restapi.reservation.entity.MeetingTime;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ReservationDTO {

    private int reservationCode;
    private LocalDate reservationDate;
    private String reservationContent;
    private UserDTO userCode;
    private Meet meetCode;
    private int[] member;
    private int[] meetTime;
    private String[] time;

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "reservationCode=" + reservationCode +
                ", reservationDate=" + reservationDate +
                ", reservationContent='" + reservationContent + '\'' +
                '}';
    }

    public ReservationDTO(int reservationCode) {
        this.reservationCode = reservationCode;
    }
}
