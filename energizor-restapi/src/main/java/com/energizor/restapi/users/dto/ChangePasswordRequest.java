package com.energizor.restapi.users.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChangePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
