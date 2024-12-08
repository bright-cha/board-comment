package com.brightcha.boardcomment.service;

import com.brightcha.boardcomment.dto.CommentRequestDto;
import com.brightcha.boardcomment.dto.CommentResponseDto;
import com.brightcha.boardcomment.dto.CommentUpdateRequestDto;
import com.brightcha.boardcomment.entity.Board;
import com.brightcha.boardcomment.entity.Comment;
import com.brightcha.boardcomment.repository.BoardRepository;
import com.brightcha.boardcomment.repository.CommentRepository;
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
@DisplayName("댓글 서비스 테스트")
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    @DisplayName("댓글 목록 조회")
    void getComments() {
        Long boardId = 1L;
        Board board = Board.create("제목", "내용", "작성자");
        ReflectionTestUtils.setField(board, "id", boardId);

        Comment comment = Comment.create("내용", "작성자", board);

        // given: 필요한 데이터와 Mock 객체의 동작 설정
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(commentRepository.findAllByBoardId(boardId)).thenReturn(List.of(comment));

        // when: 테스트 대상 메서드 호출
        List<CommentResponseDto> comments = commentService.getComments(boardId);

        // then: 결과 검증
        assertThat(comments).hasSize(1);
        verify(boardRepository).findById(boardId);
        verify(commentRepository).findAllByBoardId(boardId);
    }

    @Test
    @DisplayName("댓글 생성")
    void createComment() {
        Long boardId = 1L;
        Board board = Board.create("제목", "내용", "작성자");
        ReflectionTestUtils.setField(board, "id", boardId);

        CommentRequestDto commentRequestDto = new CommentRequestDto("내용", "작성자");

        // given: 필요한 데이터와 Mock 객체의 동작 설정
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when: 테스트 대상 메서드 호출
        CommentResponseDto responseDto = commentService.createComment(boardId, commentRequestDto);

        // then: 결과 검증
        assertThat(responseDto).isNotNull();
        verify(boardRepository).findById(boardId);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() {
        Long commentId = 2L;

        Board board = Board.create("제목", "내용", "작성자");
        Comment beforeComment = Comment.create("내용", "작성자", board);
        ReflectionTestUtils.setField(beforeComment, "id", commentId);

        CommentUpdateRequestDto updateRequestDto = new CommentUpdateRequestDto("수정된 내용");

        // given: 필요한 데이터와 Mock 객체의 동작 설정
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(beforeComment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when: 테스트 대상 메서드 호출
        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, updateRequestDto);

        // then: 결과 검증
        assertThat(commentResponseDto).isNotNull();
        verify(commentRepository).findById(commentId);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() {
        Long commentId = 1L;
        Board board = Board.create("제목", "내용", "작성자");
        Comment comment = Comment.create("내용", "작성자", board);
        ReflectionTestUtils.setField(comment, "id", commentId);

        // given: 필요한 데이터와 Mock 객체의 동작 설정
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        doNothing().when(commentRepository).delete(comment);

        // when: 테스트 대상 메서드 호출
        commentService.deleteComment(commentId);

        // then: 결과 검증
        verify(commentRepository).findById(commentId);
        verify(commentRepository).delete(comment);
    }

}