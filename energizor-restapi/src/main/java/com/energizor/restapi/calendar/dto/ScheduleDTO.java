package com.energizor.restapi.calendar.dto;

import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime schStartDate;
    private LocalDateTime schEndDate;
    private String schAllDay;
    private String schLocal;

}
