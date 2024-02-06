package com.energizor.restapi.group.service;
import com.energizor.restapi.group.dto.DeptAndTeamDTO;
import com.energizor.restapi.group.dto.TeamAndUsersDTO;
import com.energizor.restapi.group.dto.UsersDTO;
import com.energizor.restapi.group.entity.DeptAndTeam;
import com.energizor.restapi.group.entity.TeamAndUsers;
import com.energizor.restapi.group.entity.Users;
import com.energizor.restapi.group.repository.DeptGroupRepository;
import com.energizor.restapi.group.repository.TeamGroupRepository;
import com.energizor.restapi.group.repository.UserGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class GroupService {

    private final DeptGroupRepository deptGroupRepository;
    private final TeamGroupRepository teamGroupRepository;
    private final UserGroupRepository userGroupRepository;

    private final ModelMapper modelMapper;

    public GroupService(DeptGroupRepository deptGroupRepository
            , ModelMapper modelMapper
            , TeamGroupRepository teamGroupRepository
            , UserGroupRepository userGroupRepository) {
        this.deptGroupRepository = deptGroupRepository;
        this.teamGroupRepository = teamGroupRepository;
        this.userGroupRepository = userGroupRepository;
        this.modelMapper = modelMapper;

    }


    public DeptAndTeamDTO selectDeptAndTeam(int deptCode) {

        log.info("selectdepts start=============");

        DeptAndTeam dept = deptGroupRepository.findById(deptCode).get();
        DeptAndTeamDTO deptAndTeamDTO = modelMapper.map(dept, DeptAndTeamDTO.class);


        System.out.println("group ================ " + dept);

        log.info("selectdepts End===============");

        return deptAndTeamDTO;
    }


    public TeamAndUsersDTO selectTeamAndUsers(int teamCode) {

        log.info("selectTeam start=============");

        TeamAndUsers team = teamGroupRepository.findById(teamCode).get();
        TeamAndUsersDTO teamAndUsersDTO = modelMapper.map(team, TeamAndUsersDTO.class);


        System.out.println("group ================ " + team);

        log.info("selectTeam End===============");

        return teamAndUsersDTO;

    }

    public UsersDTO selectUsers(int userCode) {
        log.info("selectTeam start=============");

        Users user = userGroupRepository.findById(userCode).get();
        UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);

        System.out.println("group ================ " + user);

        log.info("selectTeam End===============");

        return usersDTO;
    }
}
