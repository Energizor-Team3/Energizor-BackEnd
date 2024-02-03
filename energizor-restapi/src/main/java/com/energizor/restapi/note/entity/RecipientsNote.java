package com.energizor.restapi.note.entity;

import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "recipients_note")
@AllArgsConstructor
@Getter
@ToString
public class RecipientsNote {

    @Id
    @JoinColumn(name = "user_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne
    private User renUserCode;

    @JoinColumn(name = "user_code")
    @OneToOne
    private User senNoteCode;;

    @Column(name = "ren_reading_state")
    private String renReadingState;

    @Column(name = "ren_reading_date")
    private Date renReadingDate;

    @Column(name = "ren_save_state")
    private String renSaveState;

    public RecipientsNote() {}

    public RecipientsNote renUserCode(User renUserCode) {
        this.renUserCode = renUserCode;
        return this;
    }

    public RecipientsNote senNoteCode(User senNoteCode) {
        this.senNoteCode = senNoteCode;
        return this;
    }

    public RecipientsNote renReadingState(String renReadingState) {
        this.renReadingState = renReadingState;
        return this;
    }

    public RecipientsNote renReadingDate(Date renReadingDate) {
        this.renReadingDate = renReadingDate;
        return this;
    }

    public RecipientsNote renSaveState(String renSaveState) {
        this.renSaveState = renSaveState;
        return this;
    }
}
