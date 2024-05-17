package com.tuture.demo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UpdateUserBasicDTO {
    @Getter
    @Setter
    @Builder
    public static class Request {
        private String nickname;
        private String profile_img;
    }
}
