package com.energizor.restapi.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Time;

@Entity
@Table(name = "reservation_time")
@AllArgsConstructor
@Getter
@ToString
public class ReservationTimeFK {

    @EmbeddedId
    private ReservationTimePK id;

    @Column(name = "meet_time", insertable=false, updatable=false)
    private Time meetTime;

    public ReservationTimeFK() {}

    public ReservationTimeFK id(ReservationTimePK id){
        this.id = id;
        return this;
    }


    public ReservationTimeFK meetTime(Time meetTime){
        this.meetTime = meetTime;
        return this;
    }
}
