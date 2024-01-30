package com.energizor.restapi.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "meet")
@AllArgsConstructor
@Getter
@ToString
public class Meet {
    @Id
    @Column(name = "meet_code")
    private int meet_code;
    @Column(name = "meet_name")
    private String meet_name;

    public Meet() {

    }
}
