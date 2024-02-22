package com.energizor.restapi.approval.dto;


import com.energizor.restapi.users.dto.TeamDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserAndTeamDTO {
    private int userCode;
    private String userId;
    private String userName;
    private String userRank;
    private String email;
    private String phone;
    private TeamDTO team;
}
