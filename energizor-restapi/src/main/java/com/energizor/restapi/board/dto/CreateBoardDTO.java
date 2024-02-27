package com.energizor.restapi.board.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreateBoardDTO {

    private String title;
    private String content;
    private int boardTypeCode;
    private boolean temporaryOpt;
}
