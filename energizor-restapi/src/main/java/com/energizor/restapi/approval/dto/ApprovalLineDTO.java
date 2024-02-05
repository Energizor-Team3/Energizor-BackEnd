package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalLineDTO {
    private int approvalLineCode;
    private DocumentDTO documentDTO;
    private UserDTO userDTO;
    private int sequence;
    private String approvalLineStatus;
    private Date processingDate;
    private String reason;

}
