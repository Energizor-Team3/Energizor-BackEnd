package com.energizor.restapi.reservation.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeetDTO {
    private int meetCode;
    private String meetName;
}
