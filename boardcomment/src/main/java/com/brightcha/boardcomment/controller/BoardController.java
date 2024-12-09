package com.brightcha.boardcomment.controller;

import com.brightcha.boardcomment.common.response.Response;
import com.brightcha.boardcomment.dto.BoardRequestDto;
import com.brightcha.boardcomment.dto.BoardResponseDto;
import com.brightcha.boardcomment.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "BoardController", description = "게시판 관련 API를 제공합니다.")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    @Operation(summary = "게시글 전체 조회", description = "모든 게시글을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 모든 게시글을 조회함."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public Response<List<BoardResponseDto>> getBoards() {
        return Response.success(boardService.getBoards());
    }

    @GetMapping("/boards/{boardId}")
    @Operation(summary = "특정 게시글 조회", description = "boardId에 해당하는 게시글을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 특정 게시글을 조회함."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public Response<BoardResponseDto> getBoard(@PathVariable Long boardId) {
        return Response.success(boardService.getBoard(boardId));
    }

    @PostMapping("/boards")
    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 생성함."),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었음."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public Response<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return Response.success(boardService.createBoard(boardRequestDto));
    }

    @PutMapping("/boards/{boardId}")
    @Operation(summary = "게시글 수정", description = "boardId에 해당하는 게시글을 수정하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 수정함."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음."),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었음."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public Response<BoardResponseDto> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        return Response.success(boardService.updateBoard(boardId, boardRequestDto));
    }

    @DeleteMapping("/boards/{boardId}")
    @Operation(summary = "게시글 삭제", description = "boardId에 해당하는 게시글을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 삭제함."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없음."),
            @ApiResponse(responseCode = "500", description = "서버 에러 발생.")
    })
    public Response<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return Response.success();
    }
}
