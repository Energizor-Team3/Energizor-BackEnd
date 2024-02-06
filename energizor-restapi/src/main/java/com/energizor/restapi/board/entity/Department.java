package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="department")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Department {

    @Id
    @Column(name="dept_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deptCode;

    @Column(name="dept_name")
    private String deptName;
}
