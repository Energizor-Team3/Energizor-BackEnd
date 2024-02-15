package com.energizor.restapi.approval.dto;


import com.energizor.restapi.users.dto.UserDTO;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProxyApprovalDTO {
    private int proxyCode;
    private UserDTO changeUser;
    private UserDTO originUser;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String proxyStatus;

}
