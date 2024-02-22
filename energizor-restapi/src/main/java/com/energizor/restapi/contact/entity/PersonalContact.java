package com.energizor.restapi.contact.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "personalContact")
@Table(name = "personal_contact")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PersonalContact{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_code", nullable = false)
    private int pcCode;

    @Column(name = "pc_name")
    private String pcName;

    @Column(name = "pc_company")
    private String pcCompany;

    @Column(name = "pc_rank")
    private String pcRank;

    @Column(name = "pc_dept")
    private String pcDept;

    @Column(name = "pc_phone")
    private String pcPhone;

    @Column(name = "pc_email")
    private String pcEmail;

    // @Column(name = "user_code")
    // private int userCode;

    @ManyToOne
    @JoinColumn(name = "user_code")
    private User user;

    public PersonalContact() {}



}
