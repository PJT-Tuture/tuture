package com.tuture.demo.controller;

import com.tuture.demo.model.domain.Board;
import com.tuture.demo.model.domain.User;
import com.tuture.demo.model.dto.AddBoardDto;
import com.tuture.demo.model.dto.BoardDetailDto;
import com.tuture.demo.model.dto.SearchCondition;
import com.tuture.demo.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<List<Board>> getBoardList() {
        List<Board> boardList = boardService.getBoardList();
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailDto.Response> getBoardDetail(@Valid @PathVariable Long id, @AuthenticationPrincipal User loginUser){
        Long userId = loginUser.getId();
        BoardDetailDto.Response boardDetail = boardService.getBoardDetail(id, userId);
        return ResponseEntity.ok(boardDetail);
    }
    /**
     * 게시글 추가
     */
    @PostMapping
    public ResponseEntity<Integer> insertBoard(@Valid @RequestBody AddBoardDto.Request request) {
        int result = boardService.addBoard(request);
        return ResponseEntity.ok(result);
//        try {
//            int result = boardService.addBoard(request);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            //Todo : 게시글 저장 실패 예외 처리 필요
//            log.debug("asdsd");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateBoard(@Valid @PathVariable Long
                                                       id, @Valid @RequestBody Board board) {
        board.setId(id);
        int result = boardService.modifyBoard(board);
        return ResponseEntity.ok(result);
//        try {
//            int result = boardService.modifyBoard(request);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
        //Todo : 유저 ID, 게시글 ID 일치x 예외 처리 필요
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteBoard(@Valid @PathVariable Long id) {

        int result = boardService.removeBoard(id);
        return ResponseEntity.ok(result);
//        try {
//            int result = boardService.removeBoard(id);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Board>> getBoardsByTagIds(@Valid @RequestParam List<Long> tagIds) {
        List<Board> boards = boardService.getBoardsByTagIds(tagIds);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Board>> search(@Valid @RequestParam(value = "page", defaultValue = "1") int page,
                                              @Valid @RequestBody SearchCondition condition) {
        List<Board> boardList = boardService.search(page, condition);
        return ResponseEntity.ok(boardList);
    }
}
