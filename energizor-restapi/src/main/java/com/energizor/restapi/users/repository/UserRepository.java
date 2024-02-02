package com.energizor.restapi.users.repository;

import com.energizor.restapi.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(String userId);

    User findByEmail(String email);

    boolean existsByUserId(String userId);
}
