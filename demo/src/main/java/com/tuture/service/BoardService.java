package com.tuture.service;


import com.tuture.model.dto.Board;
import com.tuture.model.dto.PostBoardDto;
import com.tuture.model.dto.SearchCondition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    List<Board> getBoardList();

    Board findById(Long id);

    int addBoard(PostBoardDto.Request request);

    int removeBoard(Long id);

    int modifyBoard(PostBoardDto.Request request);
    List<Board> findBoardsByTags(Long id, Long tagId);

    List<Board> search(SearchCondition condition);
}
