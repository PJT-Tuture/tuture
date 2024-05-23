package com.tuture.demo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardLanguageDto {
    long id;
    long boardId;
    long lanTagId;
}
