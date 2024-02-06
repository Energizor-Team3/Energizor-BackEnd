package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class DocumentDTO {
    private int documentCode;
    private String documentTitle;
    private UserDTO userDTO;
    private LocalDate draftDay;
    private String form;


}
