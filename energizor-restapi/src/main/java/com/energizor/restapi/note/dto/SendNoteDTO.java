package com.energizor.restapi.note.dto;

import com.energizor.restapi.group.dto.UserGroupDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    private UserGroupDTO sendUserCode;
    private List<UserGroupDTO> renUserCode;

}
