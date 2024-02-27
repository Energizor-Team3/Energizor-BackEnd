package com.energizor.restapi.note.entity;
import com.energizor.restapi.group.entity.UsersGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

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
    @CreationTimestamp
    private Timestamp noteSendDate;

    @JoinColumn(name = "send_user_code")
    @OneToOne
    private UsersGroup sendUserCode;

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

    public SendNote noteSendDate(Timestamp  noteSendDate) {
        this.noteSendDate = noteSendDate;
        return this;
    }

    public SendNote sendUserCode(UsersGroup sendUserCode) {
        this.sendUserCode = sendUserCode;
        return this;
    }

    public SendNote build() {
        return new SendNote(sendNoteCode, noteTitle, noteContent, noteSendDate, sendUserCode);
    }
}
