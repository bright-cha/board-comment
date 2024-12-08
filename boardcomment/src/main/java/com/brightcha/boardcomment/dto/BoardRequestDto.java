package com.brightcha.boardcomment.dto;

import com.brightcha.boardcomment.entity.Board;

public record BoardRequestDto(
        String title,
        String content,
        String username
) {
    public Board toEntity() {
        return Board.create(this.title, this.content, this.username);
    }
}
