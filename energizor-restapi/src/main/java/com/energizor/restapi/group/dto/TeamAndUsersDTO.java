package com.energizor.restapi.group.dto;
import com.energizor.restapi.users.entity.User;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamAndUsersDTO {

    private int teamCode;
    private String teamName;
    private int deptCode;

    private List<User> userList;

}
