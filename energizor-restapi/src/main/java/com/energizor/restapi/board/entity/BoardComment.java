package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="board_comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude="board")
public class BoardComment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentCode;

    @Column(name="comment_content")
    private String commentContent;

    @Column(name="delete_date")
    private LocalDateTime deleteDate;

    @ManyToOne
    @JoinColumn(name="board_code")
    private Board board;

    public BoardComment commentCode(int commentCode) {
        this.commentCode=commentCode;
        return this;
    }

    public BoardComment commentContent(String commentContent) {
        this.commentContent=commentContent;
        return this;
    }

    public BoardComment build() {
        return new BoardComment(commentCode,commentContent,deleteDate,board);
    }

    public void changeReplyDeleteDate(LocalDateTime deleteDate) {this.deleteDate=deleteDate;}

}
