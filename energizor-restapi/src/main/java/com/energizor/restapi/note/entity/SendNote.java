package com.energizor.restapi.note.entity;


import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "send_note")
@AllArgsConstructor
@Getter
@ToString
public class SendNote {

    @Id
    @Column(name = "send_note_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sendNoteCode;

    @Column(name = "note_title")
    private String noteTitle;

    @Column(name = "note_content")
    private String noteContent;

    @Column(name = "note_send_date")
    private Date noteSendDate;

//    @JoinColumn(name = "user_code")
//    @OneToOne
//    private User sendUserCode;
//
//    @JoinColumn(name = "user_code")
//    @OneToOne
//    private User renUserCode;

    public SendNote() {
    }

    public SendNote sendNoteCode(int sendNoteCode) {
        this.sendNoteCode = sendNoteCode;
        return this;
    }

    public SendNote noteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
        return this;
    }

    public SendNote noteContent(String noteContent) {
        this.noteContent = noteContent;
        return this;
    }

    public SendNote noteSendDate(Date noteSendDate) {
        this.noteSendDate = noteSendDate;
        return this;
    }

//    public SendNote sendUserCode(User sendUserCode) {
//        this.sendUserCode = sendUserCode;
//        return this;
//    }
//
//    public SendNote renUserCode(User renUserCode) {
//        this.renUserCode = renUserCode;
//        return this;
//    }

}
