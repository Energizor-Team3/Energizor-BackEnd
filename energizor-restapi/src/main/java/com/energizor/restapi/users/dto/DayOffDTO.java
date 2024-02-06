package com.energizor.restapi.users.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
