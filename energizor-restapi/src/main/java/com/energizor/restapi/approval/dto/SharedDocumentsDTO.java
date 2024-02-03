package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class SharedDocumentsDTO {
    private int sdCode;
    private String sdStatus;
    private UserDTO userDTO;
    private DocumentDTO documentDTO;
}
