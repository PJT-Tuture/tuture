package com.tuture.demo.model.dto;

import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UpdateUserBasicDTO {
    @Getter
    @Setter
    @Builder
    public static class Request {
        private String nickname;
        @Null
        private String profile_img;
    }
}
