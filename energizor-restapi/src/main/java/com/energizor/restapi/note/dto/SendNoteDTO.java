package com.energizor.restapi.note.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SendNoteDTO {

    private int sendNoteCode;
    private String noteTitle;
    private String noteContent;
    private Date noteSendDate;

    private UserDTO sendUserCode;
    private UserDTO renUserCode;

}
