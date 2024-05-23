package com.tuture.demo.model.dto;

import com.tuture.demo.model.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BoardListResponse {
    private int totalCnt;
    private List<Board> boards;
    private List<List<BoardLanguageDto>> tags;
}
