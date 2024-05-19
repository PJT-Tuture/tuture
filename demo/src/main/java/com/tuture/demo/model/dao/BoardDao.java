package com.tuture.demo.model.dao;

import com.tuture.demo.model.domain.Board;
import com.tuture.demo.model.dto.LanguageTagDto;
import com.tuture.demo.model.dto.SearchCondition;

import java.util.List;

public interface BoardDao {

    List<Board> getBoardList();

    Board getBoardById(Long id);
    int insertBoard(Board board);

    int updateBoard(Board board);
    int deleteBoard(Long id);
    List<Board> search(int page, SearchCondition condition);

    List<Board> getBoardsByTagIds(List<Long> tagIds);
}
