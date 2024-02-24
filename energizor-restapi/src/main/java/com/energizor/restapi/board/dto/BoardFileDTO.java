package com.energizor.restapi.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BoardFileDTO {

    private int fileCode;

    private String fileName;

    private String uuid;

    private int fileSize;

    private LocalDateTime uploadDate=LocalDateTime.now();

    private String folderPath;

    private String fileType;

    private String thumbnailPath;

    private String data;

}
