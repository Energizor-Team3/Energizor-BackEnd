package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.entity.Board;
import com.energizor.restapi.board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<BoardFile,Integer> {

    @Query("select bf "+
            "from BoardFile bf left join bf.board b "+
            "where b.boardCode= :boardCode and bf.fileName= :fileName")
    BoardFile findByBoardCodeAndFileName(@Param("boardCode")int boardCode,@Param("fileName") String fileName);
}
