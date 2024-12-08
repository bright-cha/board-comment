package com.brightcha.boardcomment.dto;

import com.brightcha.boardcomment.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long boardId,
        String content,
        String username,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getBoard().getId(),
                comment.getContent(),
                comment.getUsername(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
