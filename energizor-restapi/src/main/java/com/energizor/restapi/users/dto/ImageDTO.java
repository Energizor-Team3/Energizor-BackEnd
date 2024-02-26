package com.energizor.restapi.users.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class ImageDTO {
    private int imgCode;
    private String originalName;
    private String modifyName;
    private String status;
    private UserDTO userDTO;

}
