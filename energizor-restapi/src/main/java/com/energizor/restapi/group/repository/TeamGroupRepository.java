package com.energizor.restapi.group.repository;
import com.energizor.restapi.group.entity.TeamGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamGroupRepository extends JpaRepository<TeamGroup, Integer> {
}
