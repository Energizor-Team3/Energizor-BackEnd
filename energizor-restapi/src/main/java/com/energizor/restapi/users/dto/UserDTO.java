package com.energizor.restapi.users.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int userCode;
    private String userId;
    private String userName;
    private String userPw;
    private String userRank;
    private String email;
    private String phone;
    private Date entDate;
    private Date resignDate;


}
