package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.entity.Board;
import com.energizor.restapi.board.entity.BoardComment;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> , QuerydslPredicateExecutor<Board> {

    @Query("select b "+
            "from Board b "+
            "left join b.boardType bt "+
            "right join b.user u "+
            "where bt.boardTypeCode= :boardTypeCode "+
            "and b.deleteDate IS NULL ")
    Page<Board> findByBoardTypeCode(@Param("boardTypeCode")int boardTypeCode,Pageable paging);


    @Query("select b "+
            "from Board b left join b.user u "+
            "where b.boardCode= :boardCode " )
    Board findByCode(@Param("boardCode") int boardCode);


    @Query("select b "+
            "from Board b left join b.user u "+
            "where b.boardCode= :boardCode " +
            "and b.deleteDate is null ")
    Board findByCodeForDelete(int boardCode);
}
