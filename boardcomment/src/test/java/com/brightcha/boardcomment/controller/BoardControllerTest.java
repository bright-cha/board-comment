package com.brightcha.boardcomment.controller;

import com.brightcha.boardcomment.dto.BoardRequestDto;
import com.brightcha.boardcomment.dto.BoardResponseDto;
import com.brightcha.boardcomment.service.BoardService;
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
@DisplayName("게시물 컨트롤러 테스트")
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private BoardService boardService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시물 목록 조회")
    void getBoards() throws Exception {
        // given: 게시물이 존재한다고 가정
        // when: 게시물 목록 조회 API 호출
        mockMvc.perform(get("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                // then: 응답 상태 검증
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시물 단일 조회")
    void getBoard() throws Exception {
        // given: 게시물 생성
        BoardResponseDto boardResponseDto = createBoard("제목", "내용", "작성자");

        // when: 단일 게시물 조회 API 호출
        mockMvc.perform(get("/api/boards/{boardId}", boardResponseDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                // then: 응답 데이터 검증
                .andExpect(jsonPath("$.id").value(boardResponseDto.id()))
                .andExpect(jsonPath("$.title").value(boardResponseDto.title()))
                .andExpect(jsonPath("$.content").value(boardResponseDto.content()))
                .andExpect(jsonPath("$.username").value(boardResponseDto.username()))
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 생성")
    void createBoard() throws Exception {
        // given: 게시물 생성 요청 데이터 준비
        BoardRequestDto boardRequestDto = new BoardRequestDto("제목", "내용", "작성자");
        String requestBody = objectMapper.writeValueAsString(boardRequestDto);

        // when: 게시물 생성 API 호출
        mockMvc.perform(post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // then: 응답 데이터 검증
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(boardRequestDto.title()))
                .andExpect(jsonPath("$.content").value(boardRequestDto.content()))
                .andExpect(jsonPath("$.username").value(boardRequestDto.username()))
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 수정")
    void updateBoard() throws Exception {
        // given: 게시물 생성 및 수정 요청 데이터 준비
        BoardResponseDto boardResponseDto = createBoard("제목", "내용", "작성자");
        BoardRequestDto boardRequestDto = new BoardRequestDto("수정된 제목", "수정된 내용", "작성자");
        String requestBody = objectMapper.writeValueAsString(boardRequestDto);

        // when: 게시물 수정 API 호출
        mockMvc.perform(put("/api/boards/{boardId}", boardResponseDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // then: 응답 데이터 검증
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(boardResponseDto.id()))
                .andExpect(jsonPath("$.title").value(boardRequestDto.title()))
                .andExpect(jsonPath("$.content").value(boardRequestDto.content()))
                .andExpect(jsonPath("$.username").value(boardRequestDto.username()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 삭제")
    void deleteBoard() throws Exception {
        // given: 게시물 생성
        BoardResponseDto boardResponseDto = createBoard("제목", "내용", "작성자");

        // when: 게시물 삭제 API 호출
        mockMvc.perform(delete("/api/boards/{boardId}", boardResponseDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                // then: 응답 상태 검증
                .andExpect(status().isOk());
    }

    private BoardResponseDto createBoard(String title, String content, String username) {
        // given: 게시물 생성 요청 데이터 준비
        BoardRequestDto boardRequestDto = new BoardRequestDto(title, content, username);
        return boardService.createBoard(boardRequestDto);
    }

}