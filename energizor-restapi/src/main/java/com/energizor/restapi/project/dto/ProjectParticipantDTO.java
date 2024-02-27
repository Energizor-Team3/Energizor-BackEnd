package com.energizor.restapi.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProjectParticipantDTO {

        private int proParNo;
        private int proNo;
        private int userCode;
        private String userName;
}
