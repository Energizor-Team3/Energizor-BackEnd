package com.energizor.restapi.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TaskDTO {
    private int taskNo;
    private String taskContent;
    private String taskStatus;
    private int proParNo;
    private String userName;
}
