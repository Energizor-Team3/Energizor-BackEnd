package com.energizor.restapi.approval.repository;

import com.energizor.restapi.approval.entity.UserAndTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAndTeamRepository extends JpaRepository<UserAndTeam, Integer> {


    UserAndTeam findByUserCodeAndTeamTeamCode(int userCode, int teamCode);

    UserAndTeam findByUserCode(int userCode);
}
