package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ReferenceDTO {
    private int referenceCode;
    private String referenceStatus;
    private UserDTO userDTO;
    private DocumentDTO documentDTO;
}
