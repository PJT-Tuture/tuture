package com.tuture.demo.service;


import com.tuture.demo.model.domain.Board;
import com.tuture.demo.model.dto.AddBoardDto;
import com.tuture.demo.model.dto.SearchCondition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    List<Board> getBoardList();

    Board findBoardById(Long id);

    int addBoard(AddBoardDto.Request request);

    int removeBoard(Long id);

    int modifyBoard(Board board);

    List<Board> search(int page, SearchCondition condition);
}
