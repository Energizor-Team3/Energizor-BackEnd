package com.energizor.restapi.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAndCalendar {
    @Id
    @Column(name = "sch_no")
    private int schNo;

    @Column(name = "sch_title" )
    private String schTitle;

    @Column(name = "sch_detail" )
    private String schDetail;

    @Column(name = "sch_sdate" )
    private LocalDateTime schStartDate;

    @Column(name = "sch_edate")
    private LocalDateTime schEndDate;
    @Column(name = "sch_allday" )
    private String schAllDay;

    @Column(name = "sch_local" )
    private String schLocal;

    @ManyToOne
    @JoinColumn(name = "cal_no")
    private Calendar calendar;

    public ScheduleAndCalendar() {

    }


    public ScheduleAndCalendar build() {
        return new ScheduleAndCalendar(schNo,schTitle,schDetail,schStartDate,schEndDate,schAllDay,schLocal,calendar);
    }

}
