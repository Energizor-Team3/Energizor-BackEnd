package com.energizor.restapi.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CALENDAR")
@AllArgsConstructor
@Getter
@ToString
@Setter
@NoArgsConstructor
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





}
