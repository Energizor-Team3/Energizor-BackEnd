package com.energizor.restapi.approval.entity;


import com.energizor.restapi.users.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Getter
@ToString
public class UserAndTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private int userCode;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_rank")
    private String userRank;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @JoinColumn(name = "team_code")
    @OneToOne
    private Team team;

    public UserAndTeam() {
    }

    public UserAndTeam userCode(int userCode) {
        this.userCode = userCode;
        return this;
    }

    public UserAndTeam userId(String userId) {
        this.userId = userId;
        return this;
    }
    public UserAndTeam userName(String userName) {
        this.userName = userName;
        return this;
    }
    public UserAndTeam userRank(String userRank) {
        this.userRank = userRank;
        return this;
    }
    public UserAndTeam email(String email) {
        this.email = email;
        return this;
    }
    public UserAndTeam phone(String phone) {
        this.phone = phone;
        return this;
    }
    public UserAndTeam team(Team team) {
        this.team = team;
        return this;
    }





    public UserAndTeam build() {
        return new UserAndTeam(userCode, userId, userName, userRank, email, phone, team);
    }
}
