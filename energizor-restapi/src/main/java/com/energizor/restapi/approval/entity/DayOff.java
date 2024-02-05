package com.energizor.restapi.approval.entity;

import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Year;
import java.util.Date;

@Entity
@AllArgsConstructor

@Getter
@Table(name = "dayoff")
public class DayOff {
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
    @JoinColumn(name = "user_code")
    @OneToOne
    private User user;

    public DayOff() {
    }

    public DayOff offCode(int offCode) {
        this.offCode = offCode;
        return this;
    }

    public DayOff offYear(Year offYear) {
        this.offYear = offYear;
        return this;
    }
    public DayOff offCount(int offCount) {
        this.offCount = offCount;
        return this;
    }
    public DayOff offUsed(int offUsed) {
        this.offUsed = offUsed;
        return this;
    }
    public DayOff user(User user) {
        this.user = user;
        return this;
    }




    public DayOff build() {
        return new DayOff(offCode, offYear, offCount, offUsed, user);
    }

    @Override
    public String toString() {
        return "DayOff{" +
                "offCode=" + offCode +
                ", offYear=" + offYear +
                ", offCount=" + offCount +
                ", offUsed=" + offUsed +
                '}';
    }
}
