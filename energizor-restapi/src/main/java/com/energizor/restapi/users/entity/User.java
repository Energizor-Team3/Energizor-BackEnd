package com.energizor.restapi.users.entity;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class User {
    @Id
    @Column(name = "user_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCode;

}
