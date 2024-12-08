package com.brightcha.boardcomment.dto;

import com.brightcha.boardcomment.entity.Board;
import com.brightcha.boardcomment.entity.Comment;

public record CommentRequestDto(
        String content,
        String username
) {
    public Comment toEntity(Board board) {
        return Comment.create(this.content, this.username, board);
    }
}
