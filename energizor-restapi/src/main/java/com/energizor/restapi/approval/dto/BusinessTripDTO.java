package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BusinessTripDTO {
    private int btCode;
    private LocalDate btDate;
    private String btPhone;
    private LocalDate btStart;
    private LocalDate btFinish;
    private String btPlace;
    private String btContent;
    private DocumentDTO document;
    private UserDTO userDTO;
    private String btTitle;
    private String lineUser;
    private String rfUser;

}
