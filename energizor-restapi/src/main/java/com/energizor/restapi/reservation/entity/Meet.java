package com.energizor.restapi.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "meet")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Meet {
    @Id
    @Column(name = "meet_code")
    private int meetCode;

    @Column(name = "meet_name")
    private String meetName;

    public Meet() {}

    public Meet(int meetCode) {
        this.meetCode = meetCode;
    }

    public Meet(String meetCode) {
        this.meetCode = Integer.parseInt(meetCode);
    }

    public Meet meetCode(int meetCode){
        this.meetCode = meetCode;
        return this;
    }

    public Meet meetName(String meetName){
        this.meetName = meetName;
        return this;
    }
}
