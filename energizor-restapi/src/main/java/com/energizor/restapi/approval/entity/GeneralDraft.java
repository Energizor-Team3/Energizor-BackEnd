package com.energizor.restapi.approval.entity;

import com.energizor.restapi.approval.dto.DocumentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@Entity
@Table(name = "generaldraft")
@Getter
@AllArgsConstructor
@ToString
public class GeneralDraft {
    @Id
    @Column(name = "gd_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gdCode;
    @Column(name = "gd_date")
    private Date gdDate;
    @Column(name = "gd_title")
    private String gdTitle;
    @Column(name = "gd_content")
    private String gdContent;
    @JoinColumn(name = "document_code")
    @ManyToOne
    private Document document;

    public GeneralDraft() {
    }

    public GeneralDraft gdCode(int gdCode) {
        this.gdCode = gdCode;
        return this;
    }

    public GeneralDraft gdDate(Date gdDate) {
        this.gdDate = gdDate;
        return this;
    }
    public GeneralDraft gdTitle(String gdTitle) {
        this.gdTitle = gdTitle;
        return this;
    }
    public GeneralDraft gdContent(String gdContent) {
        this.gdContent = gdContent;
        return this;
    }
    public GeneralDraft document(Document document) {
        this.document = document;
        return this;
    }



    public GeneralDraft build() {
        return new GeneralDraft(gdCode, gdDate, gdTitle, gdContent, document);
    }
}
