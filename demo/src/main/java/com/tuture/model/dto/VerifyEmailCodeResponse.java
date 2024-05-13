package com.tuture.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyEmailCodeResponse {
    int status;
    String msg;
}
