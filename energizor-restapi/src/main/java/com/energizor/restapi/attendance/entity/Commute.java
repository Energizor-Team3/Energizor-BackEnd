package com.energizor.restapi.attendance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @ManyToOne
    @JoinColumn(name = "user_code", nullable = false)
    private User user;

    public Commute() {

    }


}