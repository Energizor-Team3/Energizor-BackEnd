package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DocumentDTO {
    private int documentCode;
    private String documentTitle;
    private String documentContent;
    private UserDTO userDTO;
    private Date draftDay;
    private String form;

}
