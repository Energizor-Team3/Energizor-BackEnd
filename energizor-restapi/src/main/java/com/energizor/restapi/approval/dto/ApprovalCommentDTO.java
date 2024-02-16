package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalCommentDTO {
    private int acCode;
    private String acContent;
    private LocalDate acDate;
    private List<DocumentDTO> documentDTO;
    private List<UserDTO> userDTO;


}
