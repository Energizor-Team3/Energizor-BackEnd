package com.energizor.restapi.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UpdateBoardCommentDTO {

    private int boardCode;
    private int commentCode;
    private String editingCommentContent;
    private LocalDateTime updateDate;
}
