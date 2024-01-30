package com.energizor.restapi.reservation.entity;

import com.energizor.restapi.users.entity.User;
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
    // reservation_code를 외래 키로 매핑
    @ManyToOne
    @JoinColumn(name = "reservation_code", referencedColumnName = "reservation_code", nullable = false)
    private Reservation reservation;

    // user_code를 외래 키로 매핑
    @ManyToOne
    @JoinColumn(name = "user_code", referencedColumnName = "user_code", nullable = false)
    private User user;

    public Attendee() {}


}
