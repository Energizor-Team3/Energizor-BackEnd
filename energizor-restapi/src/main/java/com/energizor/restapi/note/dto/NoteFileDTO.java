package com.energizor.restapi.note.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NoteFileDTO {

    private int noteFileCode;
    private String noteOriginalFileName;
    private String noteSaveFileName;
    private String noteOriginalPath;
    private String noteSavePath;

    private SendNoteDTO sendNoteDTO;

}
