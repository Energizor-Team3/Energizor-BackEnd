package com.energizor.restapi.group.dto;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeptAndTeamDTO {

        private int deptCode;
        private String deptName;

        private List<TeamGroupDTO> teamList;

}
