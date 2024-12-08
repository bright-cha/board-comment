package com.brightcha.boardcomment.service;

import com.brightcha.boardcomment.dto.BoardRequestDto;
import com.brightcha.boardcomment.dto.BoardResponseDto;
import com.brightcha.boardcomment.entity.Board;
import com.brightcha.boardcomment.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시물 서비스 테스트")
class BoardServiceImplTest {

    @InjectMocks
    private BoardServiceImpl boardService;
    @Mock
    private BoardRepository boardRepository;

    @Test
    @DisplayName("단일 게시물 조회")
    void getBoard() {
        // given: 필요한 데이터와 Mock 객체의 동작 설정
        Long boardId = 1L;
        Board board = Board.create("제목", "내용", "작성자");
        ReflectionTestUtils.setField(board, "id", boardId);

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        // when: 테스트 대상 메서드 호출
        BoardResponseDto boardResponseDto = boardService.getBoard(boardId);

        // then: 결과 검증
        assertThat(boardResponseDto.id()).isEqualTo(boardId);
        verify(boardRepository).findById(boardId);
    }

    @Test
    @DisplayName("게시물 목록 조회")
    void getBoards() {
        // given: 필요한 데이터와 Mock 객체의 동작 설정
        Board board = Board.create("제목", "내용", "작성자");
        List<Board> mockBoards = List.of(board);
        when(boardRepository.findAll()).thenReturn(mockBoards);

        // when: 테스트 대상 메서드 호출
        List<BoardResponseDto> boards = boardService.getBoards();

        // then: 결과 검증
        assertThat(boards).hasSize(1);
        verify(boardRepository).findAll();
    }

    @Test
    @DisplayName("게시물 저장")
    void createBoard() {
        // given: 필요한 데이터와 Mock 객체의 동작 설정
        Long boardId = 1L;
        BoardRequestDto boardRequestDto = new BoardRequestDto("제목", "내용", "작성자");

        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> {
            Board board = invocation.getArgument(0);
            ReflectionTestUtils.setField(board, "id", boardId);
            return board;
        });

        // when: 테스트 대상 메서드 호출
        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto);

        // then: 결과 검증
        assertThat(boardResponseDto.id()).isEqualTo(boardId);
        verify(boardRepository).save(any(Board.class));
    }

    @Test
    @DisplayName("게시물 수정")
    void updateBoard() {
        // given: 필요한 데이터와 Mock 객체의 동작 설정
        Long boardId = 1L;

        Board beforeBoard = Board.create("제목", "내용", "작성자");
        ReflectionTestUtils.setField(beforeBoard, "id", boardId);

        BoardRequestDto boardRequestDto = new BoardRequestDto("수정된 제목", "수정된 내용", "작성자");

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(beforeBoard));
        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when: 테스트 대상 메서드 호출
        BoardResponseDto boardResponseDto = boardService.updateBoard(boardId, boardRequestDto);

        // then: 결과 검증
        assertThat(boardResponseDto.id()).isEqualTo(boardId);
        assertThat(boardResponseDto.title()).isEqualTo(boardRequestDto.title());
        assertThat(boardResponseDto.content()).isEqualTo(boardRequestDto.content());

        verify(boardRepository).findById(boardId);
        verify(boardRepository).save(any(Board.class));
    }

    @Test
    @DisplayName("게시물 삭제")
    void deleteBoard() {
        // given: 필요한 데이터와 Mock 객체의 동작 설정
        Long boardId = 1L;
        Board board = Board.create("제목", "내용", "작성자");
        ReflectionTestUtils.setField(board, "id", boardId);

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        doNothing().when(boardRepository).delete(board);

        // when: 테스트 대상 메서드 호출
        boardService.deleteBoard(boardId);

        // then: 결과 검증
        verify(boardRepository).findById(boardId);
        verify(boardRepository).delete(board);
    }

}