package com.energizor.restapi.group.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserAndTeamDTO {

    private int userCode;
    private String userName;
    private TeamGroupDTO team;


}
