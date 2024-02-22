package com.energizor.restapi.group.dto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamGroupDTO {

    private int teamCode;
    private String teamName;
    private int deptCode;

    private List<UserGroupDTO> userList;
}
