package com.tuture.demo.model.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordDto {
    private String curPassword;
    private String newPassword;
}
