package com.energizor.restapi.group.service;
import com.energizor.restapi.group.dto.DeptAndTeamDTO;
import com.energizor.restapi.group.dto.TeamAndUsersDTO;
import com.energizor.restapi.group.entity.DeptAndTeam;
import com.energizor.restapi.group.entity.TeamAndUsers;
import com.energizor.restapi.group.repository.GroupRepository;
import com.energizor.restapi.group.repository.TeamGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    private final TeamGroupRepository teamGroupRepository;

    public GroupService(GroupRepository groupRepository
            , ModelMapper modelMapper
            , TeamGroupRepository teamGroupRepository) {
        this.groupRepository = groupRepository;
        this.teamGroupRepository = teamGroupRepository;
        this.modelMapper = modelMapper;

    }


    public DeptAndTeamDTO selectDeptAndTeam(int deptCode) {

        log.info("selectdepts start=============");

        DeptAndTeam dept = groupRepository.findById(deptCode).get();
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
}
