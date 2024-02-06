package com.energizor.restapi.group.repository;

import com.energizor.restapi.group.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<Users, Integer> {
}
