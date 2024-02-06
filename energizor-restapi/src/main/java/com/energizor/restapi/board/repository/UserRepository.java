package com.energizor.restapi.board.repository;

import com.energizor.restapi.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u "+
            "from User u "+
            "where u.userCode= :userCode")
    User findByCode(@Param("userCode") int userCode);

    // 관심게시판 테이블 :id.title.content,writer(작성자 이름),owner(관심게시판을 누른 사람의 유저 아이디)
}
