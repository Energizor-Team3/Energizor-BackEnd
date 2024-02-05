package com.energizor.restapi.approval.entity;

import com.energizor.restapi.approval.dto.DocumentDTO;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Entity
@ToString
@Getter
@AllArgsConstructor
@Table(name = "approvalline")
public class ApprovalLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approvalline_code")
    private int approvalLineCode;
    @JoinColumn(name = "document_code")
    @ManyToOne
    private Document document;
    @JoinColumn(name = "user_code")
    @OneToOne
    private User user;
    @Column(name = "sequence")
    private int sequence;
    @Column(name = "approvalline_status")
    private String approvalLineStatus;
    @Column(name = "processingdate")
    private Date processingDate;
    @Column(name = "reason")
    private String reason;

    public ApprovalLine() {
    }

    public ApprovalLine approvalLineCode(int approvalLineCode) {
        this.approvalLineCode = approvalLineCode;
        return this;
    }
    public ApprovalLine document(Document document) {
        this.document = document;
        return this;
    }
    public ApprovalLine user(User user) {
        this.user = user;
        return this;
    }

    public ApprovalLine sequence(int sequence) {
        this.sequence = sequence;
        return this;
    }
    public ApprovalLine approvalLineStatus(String approvalLineStatus) {
        this.approvalLineStatus = approvalLineStatus;
        return this;
    }
    public ApprovalLine processingDate(Date processingDate) {
        this.processingDate = processingDate;
        return this;
    }
    public ApprovalLine reason(String reason) {
        this.reason = reason;
        return this;
    }


    public ApprovalLine build() {
        return new ApprovalLine(approvalLineCode, document, user, sequence, approvalLineStatus, processingDate, reason);
    }
}
