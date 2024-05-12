package com.tuture.controller;

import com.tuture.global.exception.ErrorCode;
import com.tuture.global.exception.exceptionClasses.BoardException;
import com.tuture.model.dto.Board;
import com.tuture.model.dto.BoardTagDto;
import com.tuture.model.dto.PostBoardDto;
import com.tuture.model.dto.SearchCondition;
import com.tuture.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;


    @GetMapping
    public ResponseEntity<List<Board>> getBoardList(){
        List<Board> boardList = boardService.getBoardList();
        return ResponseEntity.ok(boardList);
    }

    /**
     * 게시글 추가
     */
    @PostMapping
    public ResponseEntity<Integer> insertBoard(@Valid @RequestBody PostBoardDto.Request request) {
        int result = boardService.addBoard(request);
        if (result == 0) {
            //Todo : 게시글 저장 실패 예외 처리 필요
            throw new BoardException(ErrorCode.FAILED_REGIST_BOARD);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateBoard(@Valid @PathVariable Long id, @Valid @RequestBody PostBoardDto.Request request) {
        int result = boardService.modifyBoard(request);
        if (result == 0) {
            //Todo : 게시글 저장 실패 예외 처리 필요
            throw new BoardException(ErrorCode.FAILED_REGIST_BOARD);
        }
        //Todo : 유저 ID, 게시글 ID 일치x 예외 처리 필요
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteBoard(@Valid @PathVariable Long id) {
        int result = boardService.removeBoard(id);
        if (result == 0) {
            //Todo : 게시글 삭제 실패 예외 처리 필요
            throw new BoardException(ErrorCode.FAILED_REGIST_BOARD);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<Board>> findByTagId(@Valid @ModelAttribute BoardTagDto boardTagDto){
        List<Board> boards = boardService.findBoardsByTags(boardTagDto.getTagId(), boardTagDto.getBoardId());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Board>> search(SearchCondition condition) {
        List<Board> boardList = boardService.search(condition);
        return ResponseEntity.ok(boardList);
    }
}
