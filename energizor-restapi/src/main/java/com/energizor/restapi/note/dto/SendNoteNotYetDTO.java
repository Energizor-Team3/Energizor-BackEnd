package com.energizor.restapi.note.dto;

import com.energizor.restapi.group.dto.UserGroupDTO;
import lombok.*;

import javax.xml.crypto.Data;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SendNoteNotYetDTO {

    private int notYetNoteCode;
    private String notYetTitle;
    private String notYetContent;
    private Data notYetSaveDate;

    private UserGroupDTO sendUserCode;
    private List<UserGroupDTO> renUserCode;

}
