package com.energizor.restapi.project.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class ProjectDTO {
    private int proNo;
    private String proTitle;
    private String proContent;
    private Date proStartDate;
    private Date proEndDate;
    private String proStatus;


    private List<ProjectParticipantDTO> participants = new ArrayList<>();

// 프로젝트 조회용
}
