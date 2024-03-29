package com.energizor.restapi.users.repository;

import com.energizor.restapi.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(String userId);

    User findByEmail(String email);

    boolean existsByUserId(String userId);

    User findByUserCode(int userCode);

    Optional<User> findByUserIdAndEmail(String userId, String email);

    Page<User> findByUserStatus(String y, Pageable paging);

    Page<User> findByUserStatusAndUserNameContaining(String y, String search, Pageable paging);
}
