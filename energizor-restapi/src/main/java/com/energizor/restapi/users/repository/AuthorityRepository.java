package com.energizor.restapi.users.repository;

import com.energizor.restapi.users.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Object> findByAuthName(String userRole);
}
