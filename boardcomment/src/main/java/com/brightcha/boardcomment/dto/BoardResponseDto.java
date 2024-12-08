package com.brightcha.boardcomment.dto;

import com.brightcha.boardcomment.entity.Board;

import java.time.LocalDateTime;

public record BoardResponseDto(
        Long id,
        String title,
        String content,
        String username,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static BoardResponseDto from(Board board) {
        return new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUsername(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }
}
