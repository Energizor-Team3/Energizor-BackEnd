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
    private User userCode;


    public Attendee() {}

    public Attendee attCode(int attCode){
        this.attCode= attCode;
        return this;
    }

    public Attendee reservation(Reservation reservation){
        this.reservation= reservation;
        return this;
    }
    public Attendee userCode(User userCode){
        this.userCode = userCode;
        return this;
    }


    public Attendee build() {
        return new Attendee(attCode, reservation, userCode);
    }


}
