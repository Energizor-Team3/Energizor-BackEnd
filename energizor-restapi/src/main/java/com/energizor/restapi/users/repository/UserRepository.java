package com.energizor.restapi.users.repository;

import com.energizor.restapi.calendar.entity.Schedule;
import com.energizor.restapi.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(String userId);

    User findByEmail(String email);

    boolean existsByUserId(String userId);

    User findByUserCode(int userCode);

    Optional<User> findByUserIdAndEmail(String userId, String email);


}
