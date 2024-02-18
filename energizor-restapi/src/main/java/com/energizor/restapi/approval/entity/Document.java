package com.energizor.restapi.approval.entity;


import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.naming.Name;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "document")
@AllArgsConstructor
@Getter
@ToString
public class  Document {
    @Id
    @Column(name = "document_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentCode;
    @Column(name = "document_title")
    private String documentTitle;
    @ManyToOne
    @JoinColumn(name = "user_code" )
    private User userDTO;
    @Column(name = "draftday")
    private LocalDate draftDay;
    @Column(name = "form")
    private String form;
    @Column(name = "tempsavestatus")
    private String tempSaveStatus;
    @JoinColumn(name = "ac_code")
    @OneToMany
    private List<ApprovalComment> approvalComment;


    public Document() {
    }

    public Document documentCode(Integer documentCode) {
        this.documentCode = documentCode;
        return this;
    }
    public Document documentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
        return this;
    }

    public Document userDTO(User userDTO) {
        this.userDTO = userDTO;
        return this;
    }

    public Document draftDay(LocalDate draftDay) {
        this.draftDay = draftDay;
        return this;
    }
    public Document form(String form) {
        this.form = form;
        return this;
    }
    public Document tempSaveStatus(String tempSaveStatus) {
        this.tempSaveStatus = tempSaveStatus;
        return this;
    }

    public Document approvalComment(List<ApprovalComment> approvalComment) {
        this.approvalComment = approvalComment;
        return this;
    }

    public Document build() {
        return new Document(documentCode, documentTitle, userDTO, draftDay, form, tempSaveStatus, approvalComment);
    }
}
