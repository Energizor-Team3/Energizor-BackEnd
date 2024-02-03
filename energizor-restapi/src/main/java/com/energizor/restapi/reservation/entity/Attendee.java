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
    private int attCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_code", referencedColumnName = "reservation_code")
    private Reservation reservation;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", referencedColumnName = "user_code") // 수정된 부분
    private User user;


    public Attendee() {}


}
