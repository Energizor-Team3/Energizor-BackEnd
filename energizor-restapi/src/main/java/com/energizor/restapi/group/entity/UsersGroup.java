package com.energizor.restapi.group.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity(name="groupUsers")
@Table(name = "users")
@AllArgsConstructor
@Getter
@ToString
public class UsersGroup {

    @Id
    @Column(name = "user_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    @Column(name = "user_name")
    private String userName;

    @JoinColumn(name = "team_code")
    @ManyToOne
    private TeamGroup teamGroup;

    public UsersGroup() {}

    public UsersGroup userCode(Long userCode) {
        this.userCode =userCode;
        return this;
    }

    public UsersGroup userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UsersGroup teamGroup(TeamGroup teamGroup) {
        this.teamGroup = teamGroup;
        return this;
    }

    public UsersGroup build() {
        return new UsersGroup(userCode, userName, teamGroup);
    }

}
