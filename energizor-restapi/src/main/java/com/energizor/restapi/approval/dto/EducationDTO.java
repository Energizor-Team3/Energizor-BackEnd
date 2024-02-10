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
public class EducationDTO {
    private int eduCode;
    private String eduTitle;
    private LocalDate eduDate;
    private String eduName;
    private LocalDate eduStart;
    private LocalDate eduFinish;
    private String eduInstitution;
    private int eduPrice;
    private String eduContent;
    private List<DocumentDTO> documentDTO;
    private UserDTO userDTO;
    private String lineUser;
    private String rfUser;
}
