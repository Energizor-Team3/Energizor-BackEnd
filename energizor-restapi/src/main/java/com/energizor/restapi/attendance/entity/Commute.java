package com.energizor.restapi.attendance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;

@Entity(name = "commute")
@Table(name = "commute")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_code", nullable = false)
    private int cCode;

    @Column(name = "c_date", nullable = false)
    private LocalDate cDate;

    @Column(name = "c_starttime")
    private LocalTime cStartTime;

    @Column(name = "c_endtime")
    private LocalTime cEndTime;

    @Column(name = "c_state")
    private String cState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;

    public Commute() {

    }
}