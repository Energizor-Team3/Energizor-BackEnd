package com.energizor.restapi.approval.entity;

import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "businesstrip")
@AllArgsConstructor
@ToString
@Getter
public class BusinessTrip {
    @Id
    @Column(name = "bt_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int btCode;
    @Column(name = "bt_date")
    private LocalDate btDate;
    @Column(name = "bt_phone")
    private String btPhone;
    @Column(name = "bt_start")
    private LocalDate btStart;
    @Column(name = "bt_finish")
    private LocalDate btFinish;
    @Column(name = "bt_place")
    private String btPlace;
    @Column(name = "bt_content")
    private String btContent;
    @JoinColumn(name = "document_code")
    @ManyToOne
    private Document document;
    @JoinColumn(name = "user_code")
    @ManyToOne
    private User user;
    @Column(name = "bt_title")
    private String btTitle;

    public BusinessTrip() {
    }

    public BusinessTrip btCode(int btCode) {
        this.btCode = btCode;
        return this;
    }

    public BusinessTrip btDate(LocalDate btDate) {
        this.btDate = btDate;
        return this;
    }
    public BusinessTrip btPhone(String btPhone) {
        this.btPhone = btPhone;
        return this;
    }
    public BusinessTrip btStart(LocalDate btStart) {
        this.btStart = btStart;
        return this;
    }
    public BusinessTrip btFinish(LocalDate btFinish) {
        this.btFinish = btFinish;
        return this;
    }
    public BusinessTrip btPlace(String btPlace) {
        this.btPlace = btPlace;
        return this;
    }
    public BusinessTrip btContent(String btContent) {
        this.btContent = btContent;
        return this;
    }
    public BusinessTrip document(Document document) {
        this.document = document;
        return this;
    }
    public BusinessTrip user(User user) {
        this.user = user;
        return this;
    }

    public BusinessTrip btTitle(String btTitle) {
        this.btTitle = btTitle;
        return this;
    }


    public BusinessTrip build() {
        return new BusinessTrip(btCode, btDate, btPhone, btStart, btFinish, btPlace, btContent, document, user, btTitle);
    }
}
