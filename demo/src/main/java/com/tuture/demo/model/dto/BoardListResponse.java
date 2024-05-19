package com.tuture.demo.model.dto;

import com.tuture.demo.model.domain.Board;
import lombok.Builder;

import java.util.List;

@Builder
public class BoardListResponse {
    private int totalCnt;
    private List<Board> boards;
}
