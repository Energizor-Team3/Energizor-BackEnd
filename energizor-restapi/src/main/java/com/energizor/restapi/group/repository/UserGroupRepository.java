package com.energizor.restapi.group.repository;
import com.energizor.restapi.group.entity.UsersGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UsersGroup, Integer> {
}
