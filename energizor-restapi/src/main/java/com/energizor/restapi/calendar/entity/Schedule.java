package com.energizor.restapi.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "schedule")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sch_no")
    private int schNo;

    @Column(name = "sch_title", length = 255, nullable = false)
    private String schTitle;

    @Column(name = "sch_detail", length = 3000)
    private String schDetail;

    @Column(name = "sch_sdate", nullable = false)
    private LocalDateTime schStartDate;

    @Column(name = "sch_edate")
    private LocalDateTime schEndDate;
    @Column(name = "sch_allday", length = 5, nullable = false)
    private String schAllDay;

    @Column(name = "sch_local", length = 255)
    private String schLocal;

    @ManyToOne
    @JoinColumn(name = "cal_no", nullable = false)
    private Calendar calendar;


    public Schedule() {
    }


}
