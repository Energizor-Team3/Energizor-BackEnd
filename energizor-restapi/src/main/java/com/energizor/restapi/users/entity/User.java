package com.energizor.restapi.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class User {

    @Id
    @Column(name="user_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCode;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_pw")
    private String userPw;

    @Column(name="user_rank")
    private String userRank;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="ent_date")
    private Date entDate;

    @Column(name="resign_date")
    private Date resignDate;



}