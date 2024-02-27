package com.energizor.restapi.reservation.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserDTO {
    private int userCode;
    private String name;
}
