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
@ToString
public class User {

    @Id
    @Column(name = "user_code")
    private int userCode;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_rank")
    private String userRank;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ent_date")
    private Date entDate;

    @Column(name = "resign_date")
    private Date resignDate;

    @Column(name = "user_status")
    private String userStatus;

    @OneToMany
    @JoinColumn(name = "user_code")
    private List<UserRole> userRole;

    public User() {
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

    public User build() {
        return new User(userCode, userId, userName, userPw, userRank, email, phone, entDate, resignDate, userStatus, userRole);
    }

}
