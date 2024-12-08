package com.brightcha.boardcomment.service;

import com.brightcha.boardcomment.dto.BoardRequestDto;
import com.brightcha.boardcomment.dto.BoardResponseDto;
import com.brightcha.boardcomment.entity.Board;
import com.brightcha.boardcomment.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardResponseDto getBoard(Long boardId) {
        Board board = getBoardByIdOrException(boardId);
        return BoardResponseDto.from(board);
    }

    @Override
    public List<BoardResponseDto> getBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(BoardResponseDto::from)
                .toList();
    }

    @Override
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board board = boardRepository.save(boardRequestDto.toEntity());
        return BoardResponseDto.from(board);
    }

    @Override
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Board beforeBoard = getBoardByIdOrException(boardId);
        beforeBoard.update(boardRequestDto.title(), boardRequestDto.content());
        Board afterBoard = boardRepository.save(beforeBoard);
        return BoardResponseDto.from(afterBoard);
    }

    @Override
    public void deleteBoard(Long boardId) {
        Board board = getBoardByIdOrException(boardId);
        boardRepository.delete(board);
    }

    private Board getBoardByIdOrException(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("board not found"));
    }
}
