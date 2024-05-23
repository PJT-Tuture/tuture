package com.tuture.demo.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class EmailCodeDto {
    private String email;
    private String code;
    private String expirationDate;

    public EmailCodeDto(String email, String code) {
        this.email = email;
        this.code = code;
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.expirationDate = expirationDate.format(formatter);
    }
}
