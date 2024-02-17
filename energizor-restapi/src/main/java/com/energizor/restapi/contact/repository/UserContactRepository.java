package com.energizor.restapi.contact.repository;

import com.energizor.restapi.contact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends JpaRepository<User, Integer> {
}
