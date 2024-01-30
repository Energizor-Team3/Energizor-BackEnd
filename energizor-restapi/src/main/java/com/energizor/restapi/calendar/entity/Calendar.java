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
    private int cal_no;

    @Column(name = "cal_type", length = 255, nullable = false)
    private String cal_type;

    @Column(name = "cal_color", length = 255, nullable = false)
    private String cal_color;

    @Column(name = "cal_name", length = 255, nullable = false)
    private String cal_name;

    protected Calendar() {}


    public Calendar build() {
        return new Calendar(cal_no, cal_type,cal_color,cal_name);
    }
}
