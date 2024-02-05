package com.energizor.restapi.calendar.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CalendarAndParticipantDTO {
    private int calNo;
    private String calType;
    private String calColor;
    private String calName;
    private Integer userCode;
}
