    package com.energizor.restapi.board.dto;

    import lombok.*;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public class UpdateBoardDTO {
        private int boardCode;
        private String updateTitle;
        private String updateContent;
        private int boardTypeCode;
        private String updateDate;
    }
