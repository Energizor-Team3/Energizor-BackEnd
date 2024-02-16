package com.energizor.restapi.attendance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Year;

@Entity(name = "AttendanceDayOff")
@Table(name = "dayOff")
@AllArgsConstructor
@Getter
@ToString
public class DayOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "off_code", nullable = false)
    private int offCode;

    @Column(name = "off_year")
    private Year offYear;

    @Column(name = "off_count")
    private int offCount;

    @Column(name = "off_used")
    private int offUsed;

    @Column(name = "user_code")
    private int userCode;

    public DayOff() {

    }
}
