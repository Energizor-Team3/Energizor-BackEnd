package com.energizor.restapi.project.dto;

import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProjectAndParticipantDTO {


    private String proTitle;
    private String proContent;
    private Date proStartDate;
    private Date proEndDate;
    private String proStatus;
    private List<Integer> userCodes;
}
