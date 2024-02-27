package com.energizor.restapi.note.dto;
import com.energizor.restapi.group.dto.UserGroupDTO;
import lombok.*;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SendNoteDTO {

    private int sendNoteCode;
    private String noteTitle;
    private String noteContent;
    private Timestamp noteSendDate;
    private UserGroupDTO sendUserCode;


//    private Date readingDate;
//    private String readingState;
//    private String saveType;


    @Override
    public String toString() {
        return "SendNoteDTO{" +
                "sendNoteCode=" + sendNoteCode +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", noteSendDate=" + noteSendDate +
                '}';
    }
}
