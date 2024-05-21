package com.tuture.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageTagDto {
    @JsonProperty("tag_id")
    private Long tagId;
    private String name;
}
