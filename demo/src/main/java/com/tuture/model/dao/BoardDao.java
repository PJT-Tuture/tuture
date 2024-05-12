package com.tuture.model.dao;

import com.tuture.model.dto.Board;
import com.tuture.model.dto.PostBoardDto;
import com.tuture.model.dto.SearchCondition;

import java.util.List;

public interface BoardDao {

    List<Board> getBoardList();

    Board getById(Long id);
    int insertBoard(PostBoardDto.Request request);

    int updateBoard(PostBoardDto.Request request);
    int deleteBoard(Long id);
    List<Board> findBoardsByTags(Long id, Long tagId);
    List<Board> search(SearchCondition condition);
}
