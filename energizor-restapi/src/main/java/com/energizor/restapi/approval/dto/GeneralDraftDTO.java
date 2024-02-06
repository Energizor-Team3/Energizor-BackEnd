package com.energizor.restapi.approval.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GeneralDraftDTO {
    private int gdCode;
    private Date gdDate;
    private String gdTitle;
    private String gdContent;
    private List<DocumentDTO> documentDTO;
}
