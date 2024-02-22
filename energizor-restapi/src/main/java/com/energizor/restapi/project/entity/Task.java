package com.energizor.restapi.project.entity;

import jakarta.persistence.*;
import lombok.*;

//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
//@ToString
//@Entity
//@Table(name = "task")
//public class Task {
//
//    @Id
//    @Column(name = "task_no")
//    private int taskNo;
//
//    @Column(name = "task_content")
//    private String taskContent;
//
//    @Column(name = "task_status")
//    private String taskStatus;
//
//    @ManyToOne
//    @JoinColumn(name = "pro_par_no")
//    private ProjectParticipant proParNo;
//}

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
    @Column(name = "task_no")
    private int taskNo;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "task_status")
    private String taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_par_no")
    private ProjectParticipant proParNo;
}