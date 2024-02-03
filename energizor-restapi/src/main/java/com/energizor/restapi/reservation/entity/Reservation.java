package com.energizor.restapi.reservation.entity;

import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Entity
@Table(name = "reservation")
@AllArgsConstructor
@Getter
@ToString
public class Reservation {

    @Id
    @Column(name = "reservation_code")
    private int reservationCode;
    @Column(name = "reservation_date")
    private Date reservationDate;
    @Column(name = "reservation_content")
    private String reservationContent;
    @ManyToOne
    @JoinColumn(name = "user_code")
    private User user;
    @ManyToOne
    @JoinColumn(name = "meet_code", referencedColumnName = "meet_code", nullable = false)
    private Meet meet;

    public Reservation() {

    }

    public Reservation(int reservationCode, Date reservationDate, String reservationContent) {
    }

    public Reservation reservationCode(int reservationCode){
        this.reservationCode = reservationCode;
        return this;
    }
    public Reservation reservationDate(Date reservationDate){
        this.reservationDate = reservationDate;
        return this;
    }
    public Reservation reservationContent(String reservationContent){
        this.reservationContent = reservationContent;
        return this;
    }

    public Reservation build() {
        return new Reservation(reservationCode,reservationDate,reservationContent );
    }


}
