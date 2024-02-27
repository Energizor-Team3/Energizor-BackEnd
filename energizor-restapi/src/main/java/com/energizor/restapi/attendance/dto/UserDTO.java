package com.energizor.restapi.attendance.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private int userCode;
    private String userName;
    private String teamName;
}
