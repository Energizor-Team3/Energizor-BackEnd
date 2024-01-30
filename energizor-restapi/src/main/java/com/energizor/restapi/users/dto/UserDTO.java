package com.energizor.restapi.users.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;


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

    public UserDTO() {}
}
