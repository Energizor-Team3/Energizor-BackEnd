package com.energizor.restapi.note.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RecipientsNoteDTO {

    private int renUserCode;
    private int senNoteCode;;
    private String renReadingState;
    private Date renReadingDate;
    private String renSaveState;

}
