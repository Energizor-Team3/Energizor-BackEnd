package com.energizor.restapi.approval.dto;

import lombok.*;

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
    private Date eduDate;
    private String eduName;
    private Date eduStart;
    private Date eduFinish;
    private String eduInstitution;
    private int eduPrice;
    private String eduContent;
    private List<DocumentDTO> documentDTO;
}
