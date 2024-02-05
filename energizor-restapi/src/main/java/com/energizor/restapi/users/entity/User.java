package com.energizor.restapi.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Getter

public class User {

    @Id
    @Column(name = "user_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCode;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Column(name = "user_rank", nullable = false)
    private String userRank;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ent_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date entDate;

    @Column(name = "resign_date", columnDefinition = "DATE DEFAULT '9999-12-31'", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date resignDate;

    @Column(name = "user_status", nullable = false)
    private String userStatus;

    @OneToMany
    @JoinColumn(name = "user_code")
    private List<UserRole> userRole;

    @ManyToOne
    @JoinColumn(name = "team_code")
    private Team team;

    @OneToOne
    @JoinColumn(name = "off_code")
    private Dayoff dayoff;

    @PrePersist
    public void prePersist() {
        if (this.resignDate == null) {
            this.resignDate = java.sql.Date.valueOf("9999-12-31");
        }
    }


    public User() {
        this.resignDate = java.sql.Date.valueOf("9999-12-31");
    }

    public User userCode(int userCode) {
        this.userCode = userCode;
        return this;
    }

    public User userId(String userId) {
        this.userId = userId;
        return this;
    }

    public User userName(String userName) {
        this.userName = userName;
        return this;
    }

    public User userPw(String userPw) {
        this.userPw = userPw;
        return this;
    }

    public User userRank(String userRank) {
        this.userRank = userRank;
        return this;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User phone(String phone) {
        this.phone = phone;
        return this;
    }

    public User entDate(Date entDate) {
        this.entDate = entDate;
        return this;
    }

    public User resignDate(Date resignDate) {
        this.resignDate = resignDate;
        return this;
    }

    public User userStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public User userRole(List<UserRole> userRole) {
        this.userRole = userRole;
        return this;
    }

    public User team(Team team) {
        this.team = team;
        return this;
    }

    public User dayoff(Dayoff dayoff) {
        this.dayoff = dayoff;
        return this;
    }

    public User build() {
        return new User(userCode, userId, userName, userPw, userRank, email, phone, entDate, resignDate, userStatus, userRole, team, dayoff);
    }

}
