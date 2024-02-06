package com.energizor.restapi.project.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "pro_participant")
public class ProjectParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_par_no")
    private int proParNo;

    @Column(name = "pro_no")
    private int proNo;

    @Column(name = "user_code")
    private int userCode;
}
