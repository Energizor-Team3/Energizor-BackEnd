package com.energizor.restapi.calendar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name="CALENDAR")
@AllArgsConstructor
@Getter
@ToString
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cal_no")
    private int calNo;

    @Column(name = "cal_type", length = 255, nullable = false)
    private String calType;

    @Column(name = "cal_color", length = 255, nullable = false)
    private String calColor;

    @Column(name = "cal_name", length = 255, nullable = false)
    private String calName;

    protected Calendar() {}


    public Calendar build() {
        return new Calendar(calNo,calType,calColor,calName);
    }
}
