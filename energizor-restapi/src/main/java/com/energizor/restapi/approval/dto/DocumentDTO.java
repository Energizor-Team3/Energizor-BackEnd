package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.entity.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DocumentDTO {
    private int documentCode;
    private String documentTitle;
    private String documentContent;
    private User user;

}
