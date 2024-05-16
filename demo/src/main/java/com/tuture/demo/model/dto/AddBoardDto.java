package com.tuture.demo.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tuture.demo.model.domain.Board;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



public class AddBoardDto {
    @Setter
    @Getter
    @Builder
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Request {
        @Size(min = 1, max = 50)
        private String title;
        private Long writerId;
        private String writerNickname;
        private String content;
        private String dueDate;
        private String region;
        private String roleCategory;
        private List<TagDto> tags;
        private String imgUrl;

        public static Board addBoardDtoToBoard(AddBoardDto.Request request){
            LocalDateTime regDate = LocalDateTime.now().plusMinutes(5);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return Board.builder()
                    .title(request.getTitle())
                    .writerId(request.getWriterId())
                    .writerNickname(request.getWriterNickname())
                    .content(request.getContent())
                    .viewCnt(0)
                    .dueDate(request.getDueDate())
                    .regDate(regDate.format(formatter))
                    .roleCategory(request.getRoleCategory())
                    .imgUrl(request.getImgUrl())
                    .region(request.getRegion())
            .build();
        }
    }
}
