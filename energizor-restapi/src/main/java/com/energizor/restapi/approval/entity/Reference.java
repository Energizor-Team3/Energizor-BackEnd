package com.energizor.restapi.approval.entity;

import com.energizor.restapi.approval.dto.DocumentDTO;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Year;

@Entity
@AllArgsConstructor
@Table(name = "reference")
@Getter
@ToString
public class Reference {
    @Id
    @Column(name = "reference_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int referenceCode;
    @Column(name = "reference_status")
    private String referenceStatus;
    @JoinColumn(name = "user_code")
    @ManyToOne
    private User user;
    @JoinColumn(name = "document_code")
    @ManyToOne
    private Document document;

    public Reference() {
    }

    public Reference referenceCode(int referenceCode) {
        this.referenceCode = referenceCode;
        return this;
    }

    public Reference referenceStatus(String referenceStatus) {
        this.referenceStatus = referenceStatus;
        return this;
    }
    public Reference user(User user) {
        this.user = user;
        return this;
    }
    public Reference document(Document document) {
        this.document = document;
        return this;
    }





    public Reference build() {
        return new Reference(referenceCode, referenceStatus, user, document);
    }


}
