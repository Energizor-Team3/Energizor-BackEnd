package com.energizor.restapi.attendance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommuteDTO {

    private int cCode;
    private LocalDate cDate = LocalDate.now();
    private LocalTime cStartTime;
    private LocalTime cEndTime;
    private String cState;
    private int userCode;
}
