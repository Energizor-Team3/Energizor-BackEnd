package com.energizor.restapi.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeptDTO {

    private int deptCode;

    @JsonProperty("deptName")
    private String deptName;

    @Override
    public String toString() {
        return "DeptDTO{" +
                "deptCode=" + deptCode +
                ", deptName='" + deptName + '\'' +
                '}';
    }

}
