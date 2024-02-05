package com.energizor.restapi.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_no")
    private int proNo;

    @Column(name = "pro_title")
    private String proTitle;

    @Column(name = "pro_content")
    private String proContent;

    @Column(name = "pro_sdate")
    private Date proStartDate;

    @Column(name = "pro_edate")
    private Date proEndDate;

    @Column(name = "pro_status")
    private String proStatus;

}
