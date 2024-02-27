package com.energizor.restapi.board.dto;

import com.energizor.restapi.board.entity.BaseEntity;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardCommentDTO extends BaseEntity {

    private int commentCode;
    private String commentContent;
    private LocalDateTime registerDate;
    private String deptName;
    private String teamName;
    private String userName;
    private String userRank;
    private int boardCode;

    public BoardCommentDTO(int commentCode,String commentContent,LocalDateTime registerDate,String deptName,String teamName,String userName,String userRank){
        this.commentCode=commentCode;
        this.commentContent=commentContent;
        this.registerDate=registerDate;
        this.deptName=deptName;
        this.teamName=teamName;
        this.userName=userName;
        this.userRank=userRank;
    }
}
