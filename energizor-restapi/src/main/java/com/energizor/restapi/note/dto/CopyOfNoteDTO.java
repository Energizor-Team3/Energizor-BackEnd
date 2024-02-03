package com.energizor.restapi.note.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import javax.xml.crypto.Data;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CopyOfNoteDTO {

    private int copyOfNoteCode;
    private String renTitle;
    private String renContent;
    private Data renSaveDate;

    private UserDTO sendUserCode;
    private UserDTO renUserCode;

}
