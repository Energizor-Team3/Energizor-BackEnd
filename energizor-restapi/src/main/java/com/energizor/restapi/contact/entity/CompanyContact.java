package com.energizor.restapi.contact.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
