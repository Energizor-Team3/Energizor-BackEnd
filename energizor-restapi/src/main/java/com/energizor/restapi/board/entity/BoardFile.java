package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude="board")
public class BoardFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_code")
    private int fileCode;

    @Column(name="file_name")
    private String fileName;

    @Column(name="uuid")
    private String uuid;

    @Column(name="file_size")
    private int fileSize;

    @Column(name="upload_date")
    private LocalDateTime uploadDate=LocalDateTime.now();

    @Column(name="folder_path")
    private String folderPath;

    @Column(name="file_type")
    private String fileType;

    @Column(name="thumbnail_path")
    private String thumbnailPath;

    @Column(name="data")
    private String data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_code")
    private Board board;
}
