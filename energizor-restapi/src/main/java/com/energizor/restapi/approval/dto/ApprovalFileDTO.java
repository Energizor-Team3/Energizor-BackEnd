package com.energizor.restapi.approval.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ApprovalFileDTO {
    private int apFileCode;
    private DocumentDTO documentDTO;
    private String apFileNameOrigin;
    private String apFileNameChange;
    private Date apFileDate;
    private String apFileStatus;
}
