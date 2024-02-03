package com.energizor.restapi.note.entity;

import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.xml.crypto.Data;
import java.util.Date;

@Entity
@Table(name = "copy_of_note")
@AllArgsConstructor
@Getter
@ToString
public class CopyOfNote {

    @Id
    @Column(name = "copy_of_note_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int copyOfNoteCode;

    @Column(name = "ren_title")
    private String noteTitle;

    @Column(name = "ren_content")
    private String noteContent;

    @Column(name = "ren_save_date")
    private Date noteSendDate;

//    @JoinColumn(name = "user_code")
//    @OneToOne
//    private User sendUserCode;
//
//    @JoinColumn(name = "user_code")
//    @OneToOne
//    private User renUserCode;

    public CopyOfNote(){}

    public CopyOfNote copyOfNoteCode(int copyOfNoteCode) {
        this.copyOfNoteCode = copyOfNoteCode;
        return this;
    }

    public CopyOfNote noteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
        return this;
    }

    public CopyOfNote noteContent(String noteContent) {
        this.noteContent = noteContent;
        return this;
    }

    public CopyOfNote noteSendDate(Date noteSendDate) {
        this.noteSendDate = noteSendDate;
        return this;
    }

//    public CopyOfNote sendUserCode(User sendUserCode) {
//        this.sendUserCode = sendUserCode;
//        return this;
//    }
//
//    public CopyOfNote renUserCode(User renUserCode) {
//        this.renUserCode = renUserCode;
//        return this;
//    }

}
