package com.energizor.restapi.contact.dto;
import com.energizor.restapi.users.dto.DayOffDTO;
import com.energizor.restapi.users.dto.UserRoleDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private int userCode;
    private String userName;
    private String userId;
    private String userRank;
    private String email;
    private String phone;
    private List<UserDTO> userDTO;

    private String deptName;
    private String teamName;

}
