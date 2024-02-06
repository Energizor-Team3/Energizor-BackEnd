package com.energizor.restapi.users.dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeptDTO {

    private int deptCode;
    private String deptName;

    @Override
    public String toString() {
        return "DeptDTO{" +
                "deptCode=" + deptCode +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}