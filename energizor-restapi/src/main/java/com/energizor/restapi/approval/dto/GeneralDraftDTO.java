package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GeneralDraftDTO {
    private int gdCode;
    private LocalDate gdDate;
    private String gdTitle;
    private String gdContent;
    private DocumentDTO document;
    private UserDTO userDTO;
    private String lineUser;
    private String rfUser;
}
