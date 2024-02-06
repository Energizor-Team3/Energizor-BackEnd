package com.energizor.restapi.group.service;
import com.energizor.restapi.group.dto.*;
import com.energizor.restapi.group.entity.*;
import com.energizor.restapi.group.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class GroupService {

    private final DeptGroupRepository deptGroupRepository;
    private final TeamGroupRepository teamGroupRepository;
    private final UserGroupRepository userGroupRepository;
    private final DeptRepository deptRepository;
    private final TeamRepository teamRepository;

    private final ModelMapper modelMapper;

    public GroupService(ModelMapper modelMapper
            ,DeptGroupRepository deptGroupRepository
            ,DeptRepository deptRepository
            ,TeamRepository teamRepository
            , TeamGroupRepository teamGroupRepository
            , UserGroupRepository userGroupRepository) {
        this.deptGroupRepository = deptGroupRepository;
        this.deptRepository = deptRepository;
        this.teamRepository = teamRepository;
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

    @Transactional
    public String insertDept(DeptDTO deptDTO) {
        log.info("insertDept start ===============");
        log.info("insertDept start ==============="+deptDTO);

        int result = 0;

        try {
            Dept insertDept = modelMapper.map(deptDTO, Dept.class);


            deptRepository.save(insertDept);

            result = 1;
        } catch (Exception e) {
            System.out.println("check");
            throw new RuntimeException(e);
        }

        log.info("insertDept End=============");
        return (result > 0? "부서 생성 성공": "부서 생성 실패");

    }


    public String insertTeam(TeamDTO teamDTO) {

        log.info("insertTeam start ===============");
        log.info("insertTeam start ==============="+teamDTO);

        int result = 0;

        try {
            Team insertTeam = modelMapper.map(teamDTO, Team.class);


            teamRepository.save(insertTeam);

            result = 1;
        } catch (Exception e) {
            System.out.println("check");
            throw new RuntimeException(e);
        }

        log.info("insertTeam End=============");
        return (result > 0? "팀 생성 성공": "팀 생성 실패");

    }

    public String updateDept(DeptDTO deptDTO) {

        log.info("updateDept start ===============");
        log.info("updateDept start ==============="+deptDTO);

        int result = 0;

        try {

            Dept dept = deptRepository.findById(deptDTO.getDeptCode()).get();

            dept = dept.deptCode(deptDTO.getDeptCode())
                    .deptName(deptDTO.getDeptName()).build();

            result = 1;

        } catch (Exception e) {
            log.info("[updateDept] Exception!!");
            throw new RuntimeException(e);

        }
        log.info("[DeptService] updateDept End ===================================");
        return (result > 0) ? "부서 수정 성공": "부서 수정 실패";
    }
}
