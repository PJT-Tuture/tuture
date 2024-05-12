package com.tuture.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchCondition {
    private String condition;

    private String keyword;

    private String orderBy;

    private String orderByDir;

}
