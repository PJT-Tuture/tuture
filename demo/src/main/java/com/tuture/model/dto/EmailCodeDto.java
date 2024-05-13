package com.tuture.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class EmailCodeDto {
    String email;
    String code;
    String expirationDate;

    public EmailCodeDto(String email, String code) {
        this.email = email;
        this.code = code;
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.expirationDate = expirationDate.format(formatter);
    }
}
