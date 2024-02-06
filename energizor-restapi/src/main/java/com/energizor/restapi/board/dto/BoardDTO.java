package com.energizor.restapi.board.dto;

import jdk.jshell.Snippet;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BoardDTO {

    private int boardCode;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;
    private String userRank;
    private String userName;
    private String deptName;
    private String teamName;
    private int userCode;
    private int boardTypeCode;

}
