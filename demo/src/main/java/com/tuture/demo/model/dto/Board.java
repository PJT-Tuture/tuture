package com.tuture.demo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;


@Builder
@Getter
@Setter
public class Board {
    private Long id;
    private Long writerId;
    private String writerNickname;
    private String title;
    private String regDate;
    private int viewCnt;
    private String content;
    private String region;
    private String dueDate;
    private String roleCategory;
    private String imgUrl;
}
