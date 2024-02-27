package com.energizor.restapi.group.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Entity
@Table(name = "team")
@AllArgsConstructor
@Getter
public class TeamAndUsers {


    /* 하나의 팀 안에 속해있는 사원들 전부 조회 ( 팀 + 사원s ) */

    @Id
    @Column(name = "team_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamCode;

    @Column(name = "team_name", length = 20, nullable = false)
    private String teamName;

    @OneToMany
    @JoinColumn(name = "team_code")
    private List<UsersGroup> userlist;

    public TeamAndUsers() {}

    public TeamAndUsers teamCode(Long teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public TeamAndUsers teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public TeamAndUsers userlist(List<UsersGroup> userlist) {
        this.userlist = userlist;
        return this;
    }
    public TeamAndUsers build() {
        return new TeamAndUsers(teamCode, teamName, userlist);
    }

    @Override
    public String toString() {
        return "TeamAndUsers{" +
                "teamCode=" + teamCode +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
