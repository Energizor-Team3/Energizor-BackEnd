package com.energizor.restapi.attendance.entity;

import com.energizor.restapi.users.entity.Team;
import com.energizor.restapi.users.entity.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity(name = "attendanceUser")
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

    @OneToMany
    @JoinColumn(name = "user_code")
    private List<UserRole> userRole;

    @ManyToOne
    @JoinColumn(name = "team_code")
    private Team team;



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
