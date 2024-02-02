package com.energizor.restapi.approval.entity;

import com.energizor.restapi.approval.dto.DocumentDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
    @Column(name = "")
    private int eduCode;
    @Column(name = "")
    private String eduTitle;
    @Column(name = "")
    private Date eduDate;
    @Column(name = "")
    private String eduName;
    @Column(name = "")
    private Date eduStart;
    @Column(name = "")
    private Date eduFinish;
    @Column(name = "")
    private String eduInstitution;
    @Column(name = "")
    private int eduPrice;
    @Column(name = "")
    private String eduContent;
    @JoinColumn(name = "document_code")
    @ManyToOne
    private Document document;

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
    public Education eduDate(Date eduDate) {
        this.eduDate = eduDate;
        return this;
    }
    public Education eduName(String eduName) {
        this.eduName = eduName;
        return this;
    }
    public Education eduStart(Date eduStart) {
        this.eduStart = eduStart;
        return this;
    }
    public Education eduFinish(Date eduFinish) {
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


    public Education build() {
        return new Education(eduCode, eduTitle, eduDate, eduName, eduStart, eduFinish, eduInstitution, eduPrice, eduContent, document);
    }
}
