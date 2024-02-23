package com.energizor.restapi.attendance.dto;

import lombok.*;

import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DayOffDTO {

    private int offCode;
    private Year offYear;
    private int offCount;
    private int offUsed;
    private int userCode;
}
