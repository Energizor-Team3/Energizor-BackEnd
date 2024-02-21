package com.energizor.restapi.reservation.dto;


import lombok.*;

import java.sql.Time;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeetingTimeDTO {
    private int meetTime;
    private String time; //localTime? String? Time?
}
