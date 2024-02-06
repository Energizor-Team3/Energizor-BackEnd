package com.energizor.restapi.group.repository;
import com.energizor.restapi.group.entity.TeamAndUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamGroupRepository extends JpaRepository<TeamAndUsers, Integer> {



}
