package com.energizor.restapi.attendance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommuteDTO {

    private int cCode;

    private String cState;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cDate = LocalDate.now();

    @JsonFormat(pattern = "kk:mm:ss")
    private LocalTime cStartTime = LocalTime.now();

    @JsonFormat(pattern = "kk:mm:ss")
    private LocalTime cEndTime = LocalTime.now();

    private int userCode;
    private String userName;
}