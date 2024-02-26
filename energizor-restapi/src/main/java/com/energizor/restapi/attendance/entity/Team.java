package com.energizor.restapi.attendance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "AttendanceTeam")
@Table(name = "team")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_code", nullable = false)
    private int teamCode;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    public Team() {}

    public Team teamCode(int teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public Team teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }
}
