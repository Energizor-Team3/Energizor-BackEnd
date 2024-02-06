package com.energizor.restapi.users.repository;

import com.energizor.restapi.users.entity.UserRole;
import com.energizor.restapi.users.entity.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePK> {

    @Modifying
    @Query(value = "INSERT INTO user_role (user_code, auth_code) VALUES (:userCode, :authCode)", nativeQuery = true)
    void insertUserRole(@Param("userCode") int userCode, @Param("authCode") int authCode);

    @Query("SELECT ur FROM UserRole ur WHERE ur.userCode = :userCode AND ur.authCode = :authCode")
    UserRole findByUserCodeAndAuthCode(@Param("userCode") int userCode, @Param("authCode") int authCode);

}
