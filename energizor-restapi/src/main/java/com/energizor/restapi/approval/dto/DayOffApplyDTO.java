package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class DayOffApplyDTO {
    private int offApplyCode;
    private Date offApplyDate;
    private Date offStart;
    private Date offEnd;
    private int offDay;
    private String offReason;
    private String offState;
    private DayOffDTO dayOffDTO;
    private DocumentDTO documentDTO;
    private UserDTO userDTO;
    private String offApplyTitle;
}
