package com.brightcha.boardcomment.repository;

import com.brightcha.boardcomment.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
