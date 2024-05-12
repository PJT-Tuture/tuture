package com.tuture.model.dto;

import java.util.List;

public class Board {
    private Long id;
    private String title;
    private Long writerId;
    private String content;
    private String dueDate;
    private String region;
    private String roleCategory;
    private List<TagDto> tags;
    private String imgUrl;
}
