package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="team")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Team {

    @Id
    @Column(name="team_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamCode;

    @ManyToOne
    @JoinColumn(name="dept_code")
    private Department department;

    @Column(name="team_name")
    private String teamName;
}
