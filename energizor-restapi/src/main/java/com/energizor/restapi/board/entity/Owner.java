package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name="owner_users")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Owner {

    @Id
    @Column(name = "user_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ownerCode;

    @Column(name = "user_id", nullable = false)
    private String ownerId;

    @Column(name = "user_name")
    private String ownerName;

    @Column(name = "user_pw", nullable = false)
    private String ownerPw;

    @Column(name = "user_rank", nullable = false)
    private String ownerRank;

    @Column(name = "email", nullable = false)
    private String ownerEmail;

    @Column(name = "phone")
    private String ownerPhone;

    @Column(name = "ent_date", nullable = false)
    private LocalDateTime entDate;

    @Column(name = "resign_date", columnDefinition = "DATE DEFAULT '9999-12-31'", nullable = false)
    private LocalDateTime resingDate;

    @ManyToOne
    @JoinColumn(name = "team_code")
    private Team team;

    @OneToOne
    @JoinColumn(name = "off_code")
    private DayOff dayOff;

    @Column(name = "user_status", nullable = false)
    private String userStatus;
}
