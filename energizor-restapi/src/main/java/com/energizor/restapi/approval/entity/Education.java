package com.energizor.restapi.approval.entity;

import com.energizor.restapi.approval.dto.DocumentDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "education")
@AllArgsConstructor
@ToString
@Getter
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_code")
    private int eduCode;
    @Column(name = "edu_title")
    private String eduTitle;
    @Column(name = "edu_date")
    private LocalDate eduDate;
    @Column(name = "edu_name")
    private String eduName;
    @Column(name = "edu_start")
    private LocalDate eduStart;
    @Column(name = "edu_finish")
    private LocalDate eduFinish;
    @Column(name = "edu_institution")
    private String eduInstitution;
    @Column(name = "edu_price")
    private int eduPrice;
    @Column(name = "edu_content")
    private String eduContent;
    @JoinColumn(name = "document_code")
    @OneToOne
    private Document document;
    @JoinColumn(name = "user_code")
    @OneToOne
    private User user;

    public Education() {
    }

    public Education eduCode(int eduCode) {
        this.eduCode = eduCode;
        return this;
    }

    public Education eduTitle(String eduTitle) {
        this.eduTitle = eduTitle;
        return this;
    }
    public Education eduDate(LocalDate eduDate) {
        this.eduDate = eduDate;
        return this;
    }
    public Education eduName(String eduName) {
        this.eduName = eduName;
        return this;
    }
    public Education eduStart(LocalDate eduStart) {
        this.eduStart = eduStart;
        return this;
    }
    public Education eduFinish(LocalDate eduFinish) {
        this.eduFinish = eduFinish;
        return this;
    }
    public Education eduInstitution(String eduInstitution) {
        this.eduInstitution = eduInstitution;
        return this;
    }
    public Education eduPrice(int eduPrice) {
        this.eduPrice = eduPrice;
        return this;
    }
    public Education eduContent(String eduContent) {
        this.eduContent = eduContent;
        return this;
    }

    public Education document(Document document) {
        this.document = document;
        return this;
    }
    public Education user(User user) {
        this.user = user;
        return this;
    }


    public Education build() {
        return new Education(eduCode, eduTitle, eduDate, eduName, eduStart, eduFinish, eduInstitution, eduPrice, eduContent, document, user);
    }
}
