package com.energizor.restapi.approval.entity;

import com.energizor.restapi.approval.dto.DocumentDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "approvalfile")
@AllArgsConstructor
@Getter
@ToString
public class ApprovalFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apfile_code")
    private int apFileCode;
    @JoinColumn(name = "document_code")
    @ManyToOne
    private Document document;
    @Column(name = "apfilename_origin")
    private String apFileNameOrigin;
    @Column(name = "apfilename_change")
    private String apFileNameChange;
    @Column(name = "apfile_date")
    private Date apFileDate;
    @Column(name = "apfile_status")
    private String apFileStatus;
    @Column(name = "apfilepath_origin")
    private String apFilePathOrigin;
    @Column(name = "apfilepath_change")
    private String apFilePathChange;

    public ApprovalFile() {
    }

    public ApprovalFile apFileCode(int apFileCode) {
        this.apFileCode = apFileCode;
        return this;
    }
    public ApprovalFile document(Document document) {
        this.document = document;
        return this;
    }
    public ApprovalFile apFileNameOrigin(String apFileNameOrigin) {
        this.apFileNameOrigin = apFileNameOrigin;
        return this;
    }

    public ApprovalFile apFileNameChange(String apFileNameChange) {
        this.apFileNameChange = apFileNameChange;
        return this;
    }
    public ApprovalFile apFileDate(Date apFileDate) {
        this.apFileDate = apFileDate;
        return this;
    }
    public ApprovalFile apFileStatus(String apFileStatus) {
        this.apFileStatus = apFileStatus;
        return this;
    }
    public ApprovalFile apFilePathOrigin(String apFilePathOrigin) {
        this.apFilePathOrigin = apFilePathOrigin;
        return this;
    }
    public ApprovalFile apFilePathChange(String apFilePathChange) {
        this.apFilePathChange = apFilePathChange;
        return this;
    }







    public ApprovalFile build() {
        return new ApprovalFile(apFileCode, document, apFileNameOrigin, apFileNameChange, apFileDate, apFileStatus, apFilePathOrigin, apFilePathChange);
    }

}
