package com.energizor.restapi.users.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamDTO {

    private int teamCode;
    private String teamName;
    private DeptDTO deptDTO;

    @Override
    public String toString() {
        return "TeamDTO{" +
                "teamCode=" + teamCode +
                ", teamName='" + teamName + '\'' +
                ", deptDTO=" + deptDTO +
                '}';
    }
}
