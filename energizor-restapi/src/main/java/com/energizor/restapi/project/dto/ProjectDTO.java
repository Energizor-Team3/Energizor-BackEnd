package com.energizor.restapi.project.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
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
}
