package com.brightcha.boardcomment.controller;

import com.brightcha.boardcomment.dto.CommentRequestDto;
import com.brightcha.boardcomment.dto.CommentResponseDto;
import com.brightcha.boardcomment.dto.CommentUpdateRequestDto;
import com.brightcha.boardcomment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@Tag(name = "CommentController", description = "댓글 관련 API를 제공합니다.")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/boards/{boardId}/comments")
    @Operation(summary = "댓글 목록 조회", description = "특정 게시글(boardId)에 대한 모든 댓글을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 댓글 목록을 조회함."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.getComments(boardId));
    }

    @PostMapping("/boards/{boardId}/comments")
    @Operation(summary = "댓글 생성", description = "특정 게시글(boardId)에 새로운 댓글을 생성하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 댓글을 생성함."),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었음."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId, @RequestBody CommentRequestDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(boardId, commentDto));
    }

    @PutMapping("/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "특정 댓글(commentId)을 수정하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 댓글을 수정함."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없음."),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었음."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok(commentService.updateComment(commentId, commentUpdateRequestDto));
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "특정 댓글(commentId)을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 댓글을 삭제함."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없음."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("SUCCESS");
    }
}
