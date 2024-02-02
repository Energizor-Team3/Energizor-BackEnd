package com.energizor.restapi.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Time;

@Entity
@Table(name = "meetingtime")
@AllArgsConstructor
@Getter
@ToString
public class MeetingTime {
    @Id
    @Column(name = "meet_time")
    private int meetTime;
    @Column(name = "time")
    private Time time;

    public MeetingTime() {

    }
}
