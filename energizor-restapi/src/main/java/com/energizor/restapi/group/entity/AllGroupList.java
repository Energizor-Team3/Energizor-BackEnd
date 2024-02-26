package com.energizor.restapi.group.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Entity
@Table(name = "department")
@AllArgsConstructor
@Getter
@ToString
public class AllGroupList {

    @Id
    @Column(name = "dept_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptCode;

    @Column(name = "dept_name", length = 20, nullable = false)
    private String deptName;

    @OneToMany
    @JoinColumn(name="dept_code")
    private List<TeamGroup> teamList;

    public AllGroupList() {}

    public AllGroupList deptCode(Long deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public AllGroupList deptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public AllGroupList teamList(List<TeamGroup> teamList) {
        this.teamList = teamList;
        return this;
    }

    public AllGroupList build() {
        return new AllGroupList(deptCode, deptName, teamList);
    }


}