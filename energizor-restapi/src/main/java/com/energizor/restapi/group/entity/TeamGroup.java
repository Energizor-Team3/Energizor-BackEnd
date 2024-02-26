package com.energizor.restapi.group.entity;
import com.energizor.restapi.group.dto.UserGroupDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.CustomLog;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity(name = "GroupTeam")
@Table(name = "team")
@AllArgsConstructor
@Getter
@ToString
public class TeamGroup {


    @Id
    @Column(name = "team_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamCode;


    @Column(name = "team_name", length = 20, nullable = false)
    private String teamName;

    @Column(name = "dept_code", length = 20, nullable = false)
    private Long deptCode;

    @JoinColumn(name = "team_code")
    @OneToMany
    private List<UsersGroup> userList;

    public TeamGroup() {}

    public TeamGroup teamCode(Long teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public TeamGroup teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public TeamGroup deptCode(Long deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public TeamGroup userList(List<UsersGroup> userList) {
        this.userList = userList;
        return this;
    }


    public TeamGroup build() {
        return new TeamGroup(teamCode, teamName, deptCode, userList);
    }

}
