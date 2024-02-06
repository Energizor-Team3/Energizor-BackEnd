package com.energizor.restapi.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "team")
@AllArgsConstructor
@Getter
public class Team {

    @Id
    @Column(name = "team_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamCode;


    @JoinColumn(name = "dept_code")
    @ManyToOne
    private Dept dept;

    @Column(name = "team_name", length = 20, nullable = false)
    private String teamName;

    public Team() {}

    public Team teamCode(int teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public Team dept(Dept dept) {
        this.dept = dept;
        return this;
    }

    public Team teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public Team build() {
        return new Team(teamCode, dept, teamName);
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamCode=" + teamCode +
                ", dept=" + dept +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
