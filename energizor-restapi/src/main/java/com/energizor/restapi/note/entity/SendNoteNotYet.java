package com.energizor.restapi.note.entity;
import com.energizor.restapi.group.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "copy_of_note")
@AllArgsConstructor
@Getter
@ToString
public class SendNoteNotYet {

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

    @JoinColumn(name = "user_code")
    @OneToOne
    private Users sendUserCode;

    @JoinColumn(name = "user_code")
    @OneToMany
    private List<Users> renUserCode;

    public SendNoteNotYet(){}

    public SendNoteNotYet copyOfNoteCode(int copyOfNoteCode) {
        this.copyOfNoteCode = copyOfNoteCode;
        return this;
    }

    public SendNoteNotYet noteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
        return this;
    }

    public SendNoteNotYet noteContent(String noteContent) {
        this.noteContent = noteContent;
        return this;
    }

    public SendNoteNotYet noteSendDate(Date noteSendDate) {
        this.noteSendDate = noteSendDate;
        return this;
    }

    public SendNoteNotYet sendUserCode(Users sendUserCode) {
        this.sendUserCode = sendUserCode;
        return this;
    }

    public SendNoteNotYet renUserCode(List<Users> renUserCode) {
        this.renUserCode = renUserCode;
        return this;
    }

    public SendNoteNotYet build() {
        return new SendNoteNotYet(copyOfNoteCode, noteTitle, noteContent, noteSendDate, sendUserCode, renUserCode);
    }

}
