package com.energizor.restapi.users.dto;

import lombok.*;

import java.util.LinkedHashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthorityDTO {

    private int authCode;
    private String authName;


    public static AuthorityDTO fromLinkedHashMap(LinkedHashMap<String, Object> map) {
        int authCode = (Integer) map.get("authCode");
        String authName = (String) map.get("authName");
        return new AuthorityDTO(authCode, authName);
    }
}
