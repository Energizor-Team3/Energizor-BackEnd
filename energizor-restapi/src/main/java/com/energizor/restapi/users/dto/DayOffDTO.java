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

    // 문자열 인자를 받는 생성자
    public DayOffDTO(String dayoffString) {
        String[] parts = dayoffString.split("/");
        this.offCount = Integer.parseInt(parts[0]);
        this.offUsed = Integer.parseInt(parts[1]);
    }


}
