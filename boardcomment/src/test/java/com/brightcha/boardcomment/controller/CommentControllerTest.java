package com.brightcha.boardcomment.controller;

import com.brightcha.boardcomment.dto.*;
import com.brightcha.boardcomment.service.BoardService;
import com.brightcha.boardcomment.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("댓글 컨트롤러 테스트")
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("댓글 목록 조회")
    void getComments() throws Exception {
        // given: 필요한 게시물 생성
        BoardResponseDto boardResponseDto = createBoard();

        // when: 댓글 목록 조회 API 호출
        mockMvc.perform(get("/api/boards/{boardId}", boardResponseDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 생성")
    void createComment() throws Exception {
        // given: 필요한 게시물 생성 및 요청 데이터 준비
        BoardResponseDto boardResponseDto = createBoard();

        CommentRequestDto commentRequestDto = new CommentRequestDto("내용", "작성자");
        String requestBody = objectMapper.writeValueAsString(commentRequestDto);

        // when: 댓글 생성 API 호출
        mockMvc.perform(post("/api/boards/{boardId}/comments", boardResponseDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // then: 응답 검증
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardId").value(boardResponseDto.id()))
                .andExpect(jsonPath("$.content").value(commentRequestDto.content()))
                .andExpect(jsonPath("$.username").value(commentRequestDto.username()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() throws Exception {
        // given: 필요한 게시물 및 댓글 생성
        BoardResponseDto boardResponseDto = createBoard();
        CommentResponseDto responseDto = createComment(boardResponseDto.id());

        CommentUpdateRequestDto updateRequestDto = new CommentUpdateRequestDto("수정된 내용");
        String requestBody = objectMapper.writeValueAsString(updateRequestDto);

        // when: 댓글 수정 API 호출
        mockMvc.perform(put("/api/comments/{commentId}", responseDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // then: 응답 검증
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.boardId").value(boardResponseDto.id()))
                .andExpect(jsonPath("$.content").value(updateRequestDto.content()))
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {
        // given: 필요한 게시물 및 댓글 생성
        BoardResponseDto boardResponseDto = createBoard();
        CommentResponseDto responseDto = createComment(boardResponseDto.id());

        // when: 댓글 삭제 API 호출
        mockMvc.perform(delete("/api/comments/{commentId}", responseDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                // then: 응답 상태 검증
                .andExpect(status().isOk());
    }

    private BoardResponseDto createBoard() {
        // given: 게시물 생성 요청 데이터 준비
        BoardRequestDto boardRequestDto = new BoardRequestDto("제목", "내용", "작성자");
        return boardService.createBoard(boardRequestDto);
    }

    private CommentResponseDto createComment(Long boardId) {
        // given: 댓글 생성 요청 데이터 준비
        CommentRequestDto commentRequestDto = new CommentRequestDto("내용", "작성자");
        return commentService.createComment(boardId, commentRequestDto);
    }

}