package com.brightcha.boardcomment.controller;

import com.brightcha.boardcomment.dto.CommentRequestDto;
import com.brightcha.boardcomment.dto.CommentResponseDto;
import com.brightcha.boardcomment.dto.CommentUpdateRequestDto;
import com.brightcha.boardcomment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.getComments(boardId));
    }

    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId, @RequestBody CommentRequestDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(boardId, commentDto));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok(commentService.updateComment(commentId, commentUpdateRequestDto));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("SUCCESS");
    }
}
