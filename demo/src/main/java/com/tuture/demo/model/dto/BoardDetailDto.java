package com.tuture.demo.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tuture.demo.model.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class BoardDetailDto {

    @Setter
    @Getter
    @Builder
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Response {
        private Long id;
        private Long writerId;
        private String writerNickname;
        private String title;
        private String regDate;
        private int viewCnt;
        private String content;
        private String region;
        private String dueDate;
        private String roleCategory;
        private String imgUrl;
        private boolean isWriter; // 작성자 여부

        public static Response boardToResponse(Board board, Long loggedInUserId) {
            boolean isWriter = board.getWriterId().equals(loggedInUserId);

            return Response.builder()
                    .id(board.getId())
                    .writerId(board.getWriterId())
                    .writerNickname(board.getWriterNickname())
                    .title(board.getTitle())
                    .regDate(board.getRegDate())
                    .viewCnt(board.getViewCnt())
                    .content(board.getContent())
                    .region(board.getRegion())
                    .dueDate(board.getDueDate())
                    .roleCategory(board.getRoleCategory())
                    .imgUrl(board.getImgUrl())
                    .isWriter(isWriter)
                    .build();
        }
    }
}
