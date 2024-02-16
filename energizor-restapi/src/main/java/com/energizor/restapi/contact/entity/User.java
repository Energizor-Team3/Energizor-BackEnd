package com.energizor.restapi.contact.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity(name = "user")
@Table(name = "users")
@AllArgsConstructor
@Getter
@ToString
public class User {

    @Id
    @Column(name = "user_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCode;

    @Column(name = "user_name")
    private String userName;


    public User() {}

    public User userCode(int userCode) {
        this.userCode = userCode;
        return this;
    }

    public User userName(String userName) {
        this.userName = userName;
        return this;
    }


}
