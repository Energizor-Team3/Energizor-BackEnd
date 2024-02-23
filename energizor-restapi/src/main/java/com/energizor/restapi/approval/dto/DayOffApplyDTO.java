package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor

@Setter
@Getter
public class DayOffApplyDTO {
    private int offApplyCode;
    private LocalDate offApplyDate;
    private LocalDate offStart;
    private LocalDate offEnd;
    private int offDay;
    private String offReason;
    private String offState;
    private DayOffDTO dayOffDTO;
    private DocumentDTO document;
    private UserDTO userDTO;
    private String offApplyTitle;

    private String lineUser;
    private String rfUser;




    @Override
    public String toString() {
        return "DayOffApplyDTO{" +
                "offApplyCode=" + offApplyCode +
                ", offApplyDate=" + offApplyDate +
                ", offStart=" + offStart +
                ", offEnd=" + offEnd +
                ", offDay=" + offDay +
                ", offReason='" + offReason + '\'' +
                ", offState='" + offState + '\'' +
                ", documentDTO=" + document +
                ", offApplyTitle='" + offApplyTitle + '\'' +
                '}';
    }
}
