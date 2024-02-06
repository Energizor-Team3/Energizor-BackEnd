package com.energizor.restapi.users.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Getter
@ToString
public class User {


    @Id
    @Column(name = "user_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCode;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "team_code")
    private int teamCode;


    public User() {

    }
}
