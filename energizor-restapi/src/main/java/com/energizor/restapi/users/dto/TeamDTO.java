package com.energizor.restapi.users.dto;

import com.energizor.restapi.users.entity.Dept;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamDTO {
    private int teamCode;
    private Dept dept;
    private String teamName;
}
