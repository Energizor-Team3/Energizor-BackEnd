package com.energizor.restapi.approval.entity;


import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "approvalcomment")
@AllArgsConstructor
@Getter
@ToString
public class ApprovalComment {
    @Id
    @Column(name = "ac_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int acCode;
    @Column(name = "ac_content")
    private String acContent;
    @Column(name = "ac_date")
    private Date acDate;
    @ManyToOne
    @JoinColumn(name = "document_code")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "user_code")
    private User user;

    public ApprovalComment() {
    }
    public ApprovalComment acCode(int acCode) {
        this.acCode = acCode;
        return this;
    }
    public ApprovalComment acContent(String acContent) {
        this.acContent = acContent;
        return this;
    }

    public ApprovalComment acDate(Date acDate) {
        this.acDate = acDate;
        return this;
    }
    public ApprovalComment user(User user) {
        this.user = user;
        return this;
    }

    public ApprovalComment document(Document document) {
        this.document = document;
        return this;
    }





    public ApprovalComment build() {
        return new ApprovalComment(acCode, acContent, acDate, document, user);
    }
}
