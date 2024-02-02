package com.energizor.restapi.approval.entity;

import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.naming.Name;
import java.util.Date;
@Entity
@Table(name = "document")
@AllArgsConstructor
@Getter
@ToString
public class  Document {
    @Id
    @Column(name = "document_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentCode;
    @Column(name = "document_title")
    private String documentTitle;
    @Column(name = "document_content")
    private String documentContent;
    @ManyToOne
    @JoinColumn(name = "user_code" )
    private User user;
    @Column(name = "draftday")
    private Date draftDay;
    @Column(name = "form")
    private String form;

    public Document() {
    }



    public Document documentCode(int documentCode) {
        this.documentCode = documentCode;
        return this;
    }
    public Document documentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
        return this;
    }

    public Document documentContent(String documentContent) {
        this.documentContent = documentContent;
        return this;
    }

    public Document user(User user) {
        this.user = user;
        return this;
    }

    public Document draftDay(Date draftDay) {
        this.draftDay = draftDay;
        return this;
    }
    public Document form(String form) {
        this.form = form;
        return this;
    }




    public Document build() {
        return new Document(documentCode, documentTitle, documentContent, user, draftDay, form);
    }
}
