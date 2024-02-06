package com.energizor.restapi.calendar.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CalendarDTO {

    private int calNo;
    private String calType;
    private String calColor;
    private String calName;

}
