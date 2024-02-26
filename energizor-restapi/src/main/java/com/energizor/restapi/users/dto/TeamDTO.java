package com.energizor.restapi.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamDTO {

    private int teamCode;

    @JsonProperty("teamName")
    private String teamName;
    private DeptDTO dept;

}
