package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.entity.Board;
import com.energizor.restapi.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardTypeRepository extends JpaRepository<BoardType,Integer> {

    @Query("select bt "+
            "from BoardType bt "+
            "where bt.boardTypeCode= :boardTypeCode")
    BoardType findByCode(@Param("boardTypeCode") int boardTypeCode);
    BoardType findByBoardTypeCode(int boardTypeCode);
}
