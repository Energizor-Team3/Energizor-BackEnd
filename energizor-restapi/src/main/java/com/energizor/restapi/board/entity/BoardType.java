package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="board_type")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BoardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardTypeCode;

    @Column(name="board_type")
    private String boardType;
}
