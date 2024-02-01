package com.energizor.restapi.calendar.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ScheduleDTO {
    private int schNo;
    private String schTitle;
    private String schDetail;
    private Date schStartDate;
    private Date schEndDate;
    private String schAllDay;
    private String schLocal;

}
