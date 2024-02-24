package com.energizor.restapi.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Year;

@Entity
@Table(name = "dayoff")
@AllArgsConstructor
@Getter
@ToString
public class Dayoff {

    @Id
    @Column(name = "off_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offCode;

    @Column(name = "off_year")
    private Year offYear;

    @Column(name = "off_count")
    private int offCount;

    @Column(name = "off_used")
    private int offUsed;

    @OneToOne
    @JoinColumn(name = "user_code")
    private User user;

    public Dayoff() {}

    public Dayoff offYear(Year offYear) {
        this.offYear = offYear;
        return this;
    }

    public Dayoff offCount(int offCount) {
        this.offCount = offCount;
        return this;
    }

    public Dayoff offUsed(int offUsed) {
        this.offUsed = offUsed;
        return this;
    }

    public Dayoff user(User user) {
        this.user = user;
        return this;
    }

}
