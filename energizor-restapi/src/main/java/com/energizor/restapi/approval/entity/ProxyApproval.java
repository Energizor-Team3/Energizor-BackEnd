package com.energizor.restapi.approval.entity;

import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Table(name = "proxyapproval")
@ToString
@AllArgsConstructor

public class ProxyApproval {

    @Id
    @Column(name = "proxy_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proxyCode;
    @JoinColumn(name = "change_user")
    @OneToOne
    private User changeUser;
    @JoinColumn(name = "origin_user")
    @OneToOne
    private User originUser;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "finish_date")
    private LocalDate finishDate;
    @Column(name = "proxy_status")
    private String proxyStatus;
    public ProxyApproval() {
    }

    public ProxyApproval proxyCode(int proxyCode) {
        this.proxyCode = proxyCode;
        return this;
    }
    public ProxyApproval changeUser(User changeUser) {
        this.changeUser = changeUser;
        return this;
    }
    public ProxyApproval originUser(User originUser) {
        this.originUser = originUser;
        return this;
    }
    public ProxyApproval startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }
    public ProxyApproval finishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }
    public ProxyApproval proxyStatus(String proxyStatus) {
        this.proxyStatus = proxyStatus;
        return this;
    }



    public ProxyApproval build() {
        return new ProxyApproval(proxyCode, changeUser, originUser, startDate, finishDate, proxyStatus);
    }






}
