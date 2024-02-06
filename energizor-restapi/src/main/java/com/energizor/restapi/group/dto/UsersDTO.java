package com.energizor.restapi.group.dto;
import com.energizor.restapi.group.entity.Team;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsersDTO {

    private int userCode;
    private String userName;


    private Team team;

}
