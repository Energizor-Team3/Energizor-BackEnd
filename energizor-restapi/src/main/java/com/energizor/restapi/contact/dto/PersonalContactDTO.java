package com.energizor.restapi.contact.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonalContactDTO {

    private int pcCode;
    private String pcName;
    private String pcCompany;
    private String pcRank;
    private String pcDept;
    private String pcPhone;
    private String pcEmail;
    private int userCode;
    private List<PersonalContactDTO> personalContactDTO;

}
