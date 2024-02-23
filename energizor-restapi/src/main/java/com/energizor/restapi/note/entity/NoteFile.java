package com.energizor.restapi.note.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Entity
@Table(name = "note_file")
@AllArgsConstructor
@Getter
@ToString
public class NoteFile {

    @Id
    @Column(name = "note_file_code" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteFileCode;

    @Column(name = "note_original_file_name")
    private String noteOriginalFileName;

    @Column(name = "note_save_file_name")
    private String noteSaveFileName;

    @Column(name = "note_original_path")
    private String noteOriginalPath;

    @Column(name = "note_save_path")
    private String noteSavePath;

    @JoinColumn(name = "send_note_code")
    @ManyToOne
    private SendNote sendNoteCode;

    public NoteFile() {}

    public NoteFile noteFileCode(int noteFileCode) {
        this.noteFileCode = noteFileCode;
        return this;
    }

    public NoteFile noteOriginalFileName(String noteOriginalFileName) {
        this.noteOriginalFileName = noteOriginalFileName;
        return this;
    }

    public NoteFile noteSaveFileName(String noteSaveFileName) {
        this.noteSaveFileName = noteSaveFileName;
        return this;
    }

    public NoteFile noteOriginalPath(String noteOriginalPath) {
        this.noteOriginalPath = noteOriginalPath;
        return this;
    }

    public NoteFile noteSavePath(String noteSavePath) {
        this.noteSavePath = noteSavePath;
        return this;
    }

    public NoteFile sendNoteCode(SendNote sendNoteCode) {
        this.sendNoteCode = sendNoteCode;
        return this;
    }

    public NoteFile build() {
        return new NoteFile(noteFileCode, noteOriginalFileName, noteSaveFileName, noteOriginalPath, noteSavePath ,sendNoteCode);
    }
}
