package com.energizor.restapi.group.dto;
import com.energizor.restapi.users.entity.Team;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsersDTO {

    private int userCode;
    private String userName;


    private TeamDTO team;

}
