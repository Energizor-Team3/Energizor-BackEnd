package com.energizor.restapi.contact.entity;

import com.energizor.restapi.users.entity.Dayoff;
import com.energizor.restapi.users.entity.Team;
import com.energizor.restapi.users.entity.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity(name = "user")
@Table(name = "users")
@AllArgsConstructor
@Getter
@ToString
public class User {

    @Id
    @Column(name = "user_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCode;

    @Column(name = "user_name")
    private String userName;

/*----------------------------------------------------------*/
    @Column(name = "user_id", nullable = false)
    private String userId;


    @Column(name = "user_rank", nullable = false)
    private String userRank;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;


    @OneToMany
    @JoinColumn(name = "user_code")
    private List<UserRole> userRole;

    @ManyToOne
    @JoinColumn(name = "team_code")
    private Team team;

    @OneToOne
    @JoinColumn(name = "off_code")
    private Dayoff dayoff;



    public User() {}

    public User userCode(int userCode) {
        this.userCode = userCode;
        return this;
    }

    public User userName(String userName) {
        this.userName = userName;
        return this;
    }
/* -------------------------------------------------- */
    public User userId(String userId) {
        this.userId = userId;
        return this;
    }

    public User userRank(String userRank) {
        this.userRank = userRank;
        return this;
    }

    public  User email(String email) {
        this.email = email;
        return this;
    }

    public User phone(String phone) {
        this.phone = phone;
        return this;
    }


}
