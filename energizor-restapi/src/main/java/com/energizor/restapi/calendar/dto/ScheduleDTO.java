package com.energizor.restapi.calendar.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ScheduleDTO {
    private int sch_no;
    private String sch_title;
    private String sch_detail;
    private Date sch_sdate;
    private Date sch_edate;
    private String sch_allday;
    private String sch_local;

}
