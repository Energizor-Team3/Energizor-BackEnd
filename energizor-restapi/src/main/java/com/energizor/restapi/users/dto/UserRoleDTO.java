package com.energizor.restapi.users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRoleDTO {

    private int userCode;
    private int authCode;
    private AuthorityDTO authority;

    public UserRoleDTO() {
    }

    public UserRoleDTO(int userCode, int authCode) {
        this.userCode = userCode;
        this.authCode = authCode;
    }

    public UserRoleDTO(int userCode, int authCode, AuthorityDTO authority) {
        this.userCode = userCode;
        this.authCode = authCode;
        this.authority = authority;
    }
}
