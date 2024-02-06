package com.energizor.restapi.project.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_no")
    private int taskNo;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "task_status")
    private String taskStatus;

    @ManyToOne
    @JoinColumn(name = "pro_par_no")
    private ProjectParticipant  projectParticipant;
}
