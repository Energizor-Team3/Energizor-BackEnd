package com.energizor.restapi.project.entity;

import com.energizor.restapi.users.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", referencedColumnName = "user_code", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_no", insertable = false, updatable = false)
    private Project project;
}