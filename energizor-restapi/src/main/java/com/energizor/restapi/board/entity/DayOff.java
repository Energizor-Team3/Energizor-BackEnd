package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Year;

@Entity
@Table(name="dayoff")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DayOff {

    @Id
    @Column(name="off_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offCode;

    @Column(name="off_year")
    private Year offYear;

    @Column(name="off_count")
    private int offCount;

    @Column(name="off_used")
    private int offUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User user;
}
