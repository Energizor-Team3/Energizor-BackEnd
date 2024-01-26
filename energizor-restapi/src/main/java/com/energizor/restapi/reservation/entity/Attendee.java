package com.energizor.restapi.reservation.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "attendee")
@AllArgsConstructor
@Getter
@ToString
public class Attendee {

    @Id
    @Column(name = "att_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int att_code;

    @Column(name = "reservation_code",nullable = false)
    private int reservation_code;

    @Column(name = "user_id",nullable = false)
    private int user_id;

    public Attendee() {}


}
