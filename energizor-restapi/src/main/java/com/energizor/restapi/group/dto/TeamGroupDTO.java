package com.energizor.restapi.group.dto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamGroupDTO {

    private int teamCode;
    private String teamName;
    private int deptCode;

    private List<UserGroupDTO> userList;

    @Override
    public String toString() {
        return "TeamGroupDTO{" +
                "teamCode=" + teamCode +
                ", teamName='" + teamName + '\'' +
                ", deptCode=" + deptCode +
                '}';
    }
}
