package com.energizor.restapi.calendar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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

        @Column(name = "cal_no")
        private int calNo;

    public Schedule() {

    }


    public Schedule build() {
            return new Schedule (schNo,schTitle,schDetail,schStartDate,schEndDate,schAllDay,schLocal,calNo);
        }

    }


