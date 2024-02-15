package com.energizor.restapi.group.dto;
import com.energizor.restapi.group.entity.UsersGroup;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AllGroupDTO {

    private int deptCode;
    private String deptName;

    private List<TeamGroupDTO> teamList;

//    private List<UserGroupDTO> userList;

}
