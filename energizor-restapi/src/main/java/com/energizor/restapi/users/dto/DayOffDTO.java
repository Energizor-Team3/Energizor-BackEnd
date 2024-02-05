package com.energizor.restapi.users.dto;

import lombok.*;

import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class DayOffDTO {
    private int offCode;
    private Year offYear;
    private int offCount;
    private int offUsed;
    private int userCode;
}
