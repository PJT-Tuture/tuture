package com.tuture.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostBoardDto {
    @Setter
    public static class Request {
        @Size(min = 1, max = 50)
        private String title;
        private Long writerId;
        private String content;
        private String dueDate;
        private String region;
        private String roleCategory;
        private List<TagDto> tags;
        private String imgUrl;
    }

}
