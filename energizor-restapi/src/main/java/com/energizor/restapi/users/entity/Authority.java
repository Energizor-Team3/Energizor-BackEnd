package com.energizor.restapi.users.entity;

import ch.qos.logback.core.testUtil.RandomUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "authority")
@AllArgsConstructor
@Getter
@ToString
public class Authority {

    @Id
    @Column(name = "auth_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authCode;

    @Column(name = "auth_name", nullable = false)
    private String authName;

    public Authority() {
    }

    public Authority authCode(int authCode) {
        this.authCode = authCode;
        return this;
    }

    public Authority authName(String authName) {
        this.authName = authName;
        return this;
    }

    public Authority build() {
        return new Authority(authCode, authName);
    }
}
