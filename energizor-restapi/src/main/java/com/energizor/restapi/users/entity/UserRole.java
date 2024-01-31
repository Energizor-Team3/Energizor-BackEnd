package com.energizor.restapi.users.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "user_role")
@IdClass(UserRolePK.class)
@Getter
@ToString
public class UserRole {

    @Id
    @Column(name = "user_code", nullable = false)
    private int userCode;

    @Id
    @Column(name = "auth_code", nullable = false)
    private int authCode;

    @ManyToOne
    @JoinColumn(name = "auth_code", insertable = false, updatable = false)
    private Authority authority;

    public UserRole() {
    }

    public UserRole(int userCode, int authCode) {
        this.userCode = userCode;
        this.authCode = authCode;
    }

    public UserRole(int userCode, int authCode, Authority authority) {
        this.userCode = userCode;
        this.authCode = authCode;
        this.authority = authority;
    }

    public UserRole userCode(int userCode) {
        this.userCode = userCode;
        return this;
    }

    public UserRole authCode(int authCode) {
        this.authCode = authCode;
        return this;
    }

    public UserRole authority(Authority authority) {
        this.authority = authority;
        return this;
    }

    public UserRole build() {
        return new UserRole(userCode, authCode, authority);
    }
}
