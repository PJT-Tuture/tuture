package com.tuture.demo.service;


import com.tuture.demo.model.domain.Board;
import com.tuture.demo.model.dto.AddBoardDto;
import com.tuture.demo.model.dto.BoardDetailDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    List<Board> getBoardList();

    Board findBoardById(Long id);

    BoardDetailDto.Response getBoardDetail(Long boardId, Long loggedInUserId);

    int addBoard(AddBoardDto.Request request);

    int removeBoard(Long id);

    int modifyBoard(Board board);

    List<Board> getBoardsByTagIds(List<Long> tagIds);
}
