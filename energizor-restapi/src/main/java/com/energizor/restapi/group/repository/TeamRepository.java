package com.energizor.restapi.group.repository;

import com.energizor.restapi.users.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
