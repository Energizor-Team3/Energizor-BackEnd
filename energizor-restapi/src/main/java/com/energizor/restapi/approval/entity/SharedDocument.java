package com.energizor.restapi.approval.entity;

import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@Table(name = "shareddocuments")
@Getter
@ToString
public class SharedDocument {
    @Id
    @Column(name = "sd_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sdCode;
    @Column(name = "sd_status")
    private String sdStatus;
    @JoinColumn(name = "user_code")
    @ManyToOne
    private User user;
    @JoinColumn(name = "document_code")
    @ManyToOne
    private Document document;

    public SharedDocument() {
    }

    public SharedDocument sdCode(int sdCode) {
        this.sdCode = sdCode;
        return this;
    }

    public SharedDocument sdStatus(String sdStatus) {
        this.sdStatus = sdStatus;
        return this;
    }
    public SharedDocument user(User user) {
        this.user = user;
        return this;
    }
    public SharedDocument document(Document document) {
        this.document = document;
        return this;
    }





    public SharedDocument build() {
        return new SharedDocument(sdCode, sdStatus, user, document);
    }


}
