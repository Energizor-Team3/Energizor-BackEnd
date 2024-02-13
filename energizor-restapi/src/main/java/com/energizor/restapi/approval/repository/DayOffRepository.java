package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.DayOff;
import com.energizor.restapi.approval.entity.Document;
import com.energizor.restapi.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface DayOffRepository extends JpaRepository<DayOff, Integer> {







//@Query ("SELECT * FROM dayoff WHERE user = user AND offYear = offYear")
//    DayOff findByUserAndOffYear(User user, Year offYear);

    @Query("SELECT d FROM DayOff d WHERE d.user = :user AND d.offYear = :offYear")
    DayOff findByUserAndOffYear(@Param("user") User user, @Param("offYear") Year offYear);

    DayOff findByUser(User userDTO);
}
