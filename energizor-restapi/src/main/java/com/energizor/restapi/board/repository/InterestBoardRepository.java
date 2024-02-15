package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.dto.InterestBoardDTO;
import com.energizor.restapi.board.entity.InterestBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InterestBoardRepository extends JpaRepository<InterestBoard,Integer> , QuerydslPredicateExecutor<InterestBoard> {

    @Query("select ib "+
            "from InterestBoard ib "+
            "where ib.interestCode= :interestCode")
    InterestBoard findByInterestCode(int interestCode);


    @Query(value = "select ib, b, u, count(bc) "+
            "from InterestBoard ib "+
            "left join ib.board b "+
            "left join ib.user u "+
            "left join BoardComment bc on bc.board=b "+
            "group by ib ",
                countQuery = "select count(ib) from InterestBoard ib"
    )
    Page<Object[]> findInterestWithReplyCount(Pageable interestCode);

    @Query("select ib, b, u " +
            "from InterestBoard ib "+
            "left join ib.board b "+
            "left join b.user u "+
            "where ib.interestCode= :interestCode")
    Object findDetailByInterestCode(@Param("interestCode")int interestCode);


}
