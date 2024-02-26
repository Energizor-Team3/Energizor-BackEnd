package com.energizor.restapi.group.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DeptGroupDTO {

    private int deptCode;
    private String deptName;

    @Override
    public String toString() {
        return "DeptGroupDTO{" +
                "deptCode=" + deptCode +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
