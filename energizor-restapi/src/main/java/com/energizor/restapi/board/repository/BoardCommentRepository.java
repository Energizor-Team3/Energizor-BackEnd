package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardCommentRepository extends JpaRepository<BoardComment,Integer> {

    @Query("select bc "+
            "from BoardComment bc "+
            "left join bc.board b "+
            "where b.boardCode= :boardCode " +
            "and bc.deleteDate IS NULL ")
    List<BoardComment> findByCodeWithComment(@Param("boardCode") int boardCode);

    @Query("select bc "+
            "from BoardComment bc "+
            "where bc.commentCode= :commentCode")
    BoardComment findByCommentCode(@Param("commentCode") int commentCode);

    @Query("select bc "+
            "from BoardComment bc "+
            "where bc.board.boardCode= :boardCode "+
            "and bc.board.deleteDate IS NULL ")
    Optional<List<BoardComment>> findByBoardCode(@Param("boardCode") int boardCode);
}
