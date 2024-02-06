package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class User {

    @Id
    @Column(name="user_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCode;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_pw")
    private String userPw;

    @Column(name="user_rank")
    private String userRank;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="ent_date")
    private Date entDate;

    @Column(name="resign_date")
    private Date resignDate;

    @ManyToOne
    @JoinColumn(name="team_code")
    private Team team;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="off_code")
    private DayOff dayOff;

    @OneToMany(mappedBy = "user")
    private List<InterestBoard> interestBoards=new ArrayList<>();


}
