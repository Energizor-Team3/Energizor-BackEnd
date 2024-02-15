package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.entity.InterestBoard;
import com.energizor.restapi.board.entity.TemporaryBoard;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface TemporaryBoardRepository extends JpaRepository<TemporaryBoard,Integer>, QuerydslPredicateExecutor<TemporaryBoard> {

    @Query("select tb "+
            "from TemporaryBoard tb "+
            "where tb.temporaryCode= :temporaryCode")
    TemporaryBoard findByTemporaryCode(@Param("temporaryCode")int temporaryCode);


}
