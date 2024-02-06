package com.energizor.restapi.users.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamDTO {

    private int teamCode;
    private String teamName;
    private DeptDTO deptDTO;

}
