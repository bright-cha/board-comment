package com.brightcha.boardcomment.controller;

import com.brightcha.boardcomment.dto.BoardRequestDto;
import com.brightcha.boardcomment.dto.BoardResponseDto;
import com.brightcha.boardcomment.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        return ResponseEntity.ok(boardService.getBoards());
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @PostMapping("/boards")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(boardService.createBoard(boardRequestDto));
    }

    @PutMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, boardRequestDto));
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok("SUCCESS");
    }
}
