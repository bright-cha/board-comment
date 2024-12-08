package com.brightcha.boardcomment.service;

import com.brightcha.boardcomment.dto.BoardRequestDto;
import com.brightcha.boardcomment.dto.BoardResponseDto;

import java.util.List;

public interface BoardService {
    BoardResponseDto getBoard(Long boardId);
    List<BoardResponseDto> getBoards();
    BoardResponseDto createBoard(BoardRequestDto boardRequestDto);
    BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto);
    void deleteBoard(Long boardId);
}
