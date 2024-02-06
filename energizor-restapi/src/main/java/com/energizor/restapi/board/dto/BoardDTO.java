package com.energizor.restapi.board.dto;

import lombok.*;
import java.time.LocalDateTime;
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
