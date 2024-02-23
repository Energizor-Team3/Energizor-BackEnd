package com.energizor.restapi.users.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "department")
@AllArgsConstructor
@Getter
public class Dept {

    @Id
    @Column(name = "dept_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deptCode;

    @Column(name = "dept_name", length = 20, nullable = false)
    private String deptName;

    public Dept() {}

    public Dept deptCode(int deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public Dept deptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public Dept build() {
        return new Dept(deptCode, deptName);
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptCode=" + deptCode +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
