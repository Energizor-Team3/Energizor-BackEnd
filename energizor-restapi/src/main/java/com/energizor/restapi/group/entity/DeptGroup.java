package com.energizor.restapi.group.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity(name="groupDept")
@Table(name = "department")
@AllArgsConstructor
@Getter
@ToString
public class DeptGroup {

    @Id
    @Column(name = "dept_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptCode;

    @Column(name = "dept_name", length = 20, nullable = false)
    private String deptName;

    public DeptGroup() {}

    public DeptGroup deptCode(Long deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public DeptGroup deptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public DeptGroup build() {
        return new DeptGroup(deptCode, deptName);
    }

}
