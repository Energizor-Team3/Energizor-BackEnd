package com.energizor.restapi.reservation.entity;

import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@Getter


public class Reservation {

    @Id
    @Column(name = "reservation_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationCode;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "reservation_content")
    private String reservationContent;

    @ManyToOne
    @JoinColumn(name = "user_code")
    private User userCode;

    @ManyToOne
    @JoinColumn(name = "meet_code")
    private Meet meetCode;











    public Reservation() {

    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationCode=" + reservationCode +
                ", reservationDate=" + reservationDate +
                ", reservationContent='" + reservationContent + '\'' +
                '}';
    }

    public Reservation reservationCode(int reservationCode){
        this.reservationCode = reservationCode;
        return this;
    }
    public Reservation reservationDate(LocalDate reservationDate){
        this.reservationDate = reservationDate;
        return this;
    }
    public Reservation reservationContent(String reservationContent){
        this.reservationContent = reservationContent;
        return this;
    }
    public Reservation userCode(User userCode){
        this.userCode = userCode;
        return this;
    }
    public Reservation meetCode(Meet meetCode){
        this.meetCode = meetCode;
        return this;
    }


    public Reservation build() {
        return new Reservation(reservationCode,reservationDate,reservationContent,userCode,meetCode );
    }


}
