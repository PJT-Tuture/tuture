package com.tuture.demo.service;

import com.tuture.demo.model.dao.BoardDao;
import com.tuture.demo.model.dao.BoardLanguageDao;
import com.tuture.demo.model.domain.Board;
import com.tuture.demo.model.dto.AddBoardDto;
import com.tuture.demo.model.dto.BoardDetailDto;
import com.tuture.demo.model.dto.LanguageTagDto;
import com.tuture.demo.model.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardDao boardDao;
    private final BoardLanguageDao boardLanguageDao;
    @Override
    public List<Board> getBoardList(){
        return boardDao.getBoardList();
    }

    @Override
    public Board findBoardById(Long id) {
        return boardDao.getBoardById(id);
    }

    @Override
    public BoardDetailDto.Response getBoardDetail(Long boardId, Long loggedInUserId) {
        Board board = findBoardById(boardId);
        return BoardDetailDto.Response.boardToResponse(board, loggedInUserId);
    }
    @Override
    @Transactional
    public int addBoard(AddBoardDto.Request request) {
        Board board = AddBoardDto.Request.addBoardDtoToBoard(request);
        boardDao.insertBoard(board);
        for (LanguageTagDto tag : request.getTags()) {
            boardLanguageDao.insertBoardLanguage(board.getId(), tag.getTagId());
        }
        return board.getId().intValue();
    }

    @Override
    @Transactional
    public int modifyBoard(Board board) {
        return boardDao.updateBoard(board);
    }

    @Override
    @Transactional
    public int removeBoard(Long id) {
        return boardDao.deleteBoard(id);
    }

    @Override
    public List<Board> search(int page, SearchCondition condition) {
        return boardDao.search(page, condition);
    }

    @Override
    public List<Board> getBoardsByTagIds(List<Long> tagIds){
        return boardDao.getBoardsByTagIds(tagIds);
    }
}
