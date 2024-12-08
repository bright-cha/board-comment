package com.brightcha.boardcomment.service;

import com.brightcha.boardcomment.dto.CommentRequestDto;
import com.brightcha.boardcomment.dto.CommentResponseDto;
import com.brightcha.boardcomment.dto.CommentUpdateRequestDto;
import com.brightcha.boardcomment.entity.Board;
import com.brightcha.boardcomment.entity.Comment;
import com.brightcha.boardcomment.repository.BoardRepository;
import com.brightcha.boardcomment.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<CommentResponseDto> getComments(Long boardId) {

        getBoardByIdOrException(boardId);

        List<Comment> comments = commentRepository.findAllByBoardId(boardId);

        return comments.stream()
                .map(CommentResponseDto::from)
                .toList();
    }

    @Override
    public CommentResponseDto createComment(Long boardId, CommentRequestDto commentRequestDto) {
        Board board = getBoardByIdOrException(boardId);
        Comment comment = commentRepository.save(Comment.create(commentRequestDto.content(), commentRequestDto.username(), board));
        return CommentResponseDto.from(comment);
    }

    @Override
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment beforeComment = getCommentByIdOrException(commentId);
        beforeComment.update(commentUpdateRequestDto.content());
        Comment afterComment = commentRepository.save(beforeComment);
        return CommentResponseDto.from(afterComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = getCommentByIdOrException(commentId);
        commentRepository.delete(comment);
    }

    private Board getBoardByIdOrException(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("board not found"));
    }

    private Comment getCommentByIdOrException(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("comment not found"));
    }
}
