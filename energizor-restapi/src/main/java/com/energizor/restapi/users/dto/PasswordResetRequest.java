package com.energizor.restapi.users.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PasswordResetRequest {

    private String userId;
    private String email;
}
