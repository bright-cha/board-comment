package com.brightcha.boardcomment.service;

import com.brightcha.boardcomment.dto.CommentRequestDto;
import com.brightcha.boardcomment.dto.CommentResponseDto;
import com.brightcha.boardcomment.dto.CommentUpdateRequestDto;
import com.brightcha.boardcomment.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> getComments(Long boardId);
    CommentResponseDto createComment(Long boardId, CommentRequestDto commentRequestDto);
    CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto);
    void deleteComment(Long commentId);
}
