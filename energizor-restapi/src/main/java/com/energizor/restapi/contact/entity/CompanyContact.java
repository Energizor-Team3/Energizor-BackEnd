package com.energizor.restapi.contact.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "companyContact")
@Table(name = "company_contact")
@AllArgsConstructor
@Getter
@ToString
public class CompanyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cp_code", nullable = false)
    private int cpCode;

    @Column(name = "user_code")
    private int userCode;

    public CompanyContact() {}

}
