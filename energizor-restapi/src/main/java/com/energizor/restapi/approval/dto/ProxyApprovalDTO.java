package com.energizor.restapi.approval.dto;


import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProxyApprovalDTO {
private DocumentDTO documentDTO;
private UserDTO changeUser;
private UserDTO originUser;
}
