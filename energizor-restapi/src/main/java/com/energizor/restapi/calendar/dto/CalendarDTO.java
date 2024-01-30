package com.energizor.restapi.calendar.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CalendarDTO {

    private int cal_no;
    private String cal_type;
    private String cal_color;
    private String cal_name;

}
