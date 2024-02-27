package com.energizor.restapi.group.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAndTeamDTO {

    private int userCode;
    private String userName;
    private TeamGroupDTO team;

    @Override
    public String toString() {
        return "UserAndTeamDTO{" +
                "userCode=" + userCode +
                ", userName='" + userName + '\'' +
                ", team=" + team +
                '}';
    }
}
