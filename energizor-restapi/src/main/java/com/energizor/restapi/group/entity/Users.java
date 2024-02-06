package com.energizor.restapi.group.entity;

import com.energizor.restapi.users.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "GroupUsers")
@AllArgsConstructor
@Getter
@ToString
public class Users {

    @Id
    @Column(name = "user_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    @Column(name = "user_name")
    private String userName;

    @JoinColumn(name = "team_code")
    @ManyToOne
    private Team team;

    public Users() {}

    public Users userCode(Long userCode) {
        this.userCode =userCode;
        return this;
    }

    public Users userName(String userName) {
        this.userName = userName;
        return this;
    }

    public Users team(Team team) {
        this.team = team;
        return this;
    }

    public Users build() {
        return new Users(userCode, userName, team);
    }

}
