package com.energizor.restapi.users.repository;

import com.energizor.restapi.users.entity.UserRole;
import com.energizor.restapi.users.entity.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePK> {
}
