package com.energizor.restapi.group.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "team")
@AllArgsConstructor
@Getter
@ToString
public class Team {


    @Id
    @Column(name = "team_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamCode;

    @Column(name = "team_name", length = 20, nullable = false)
    private String teamName;

    public Team() {}

    public Team teamCode(Long teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public Team teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }


    public Team build() {
        return new Team(teamCode, teamName);
    }

}
