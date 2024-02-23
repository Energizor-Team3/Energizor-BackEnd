package com.energizor.restapi.group.service;
import com.energizor.restapi.approval.dto.UserAndTeamDTO;
import com.energizor.restapi.approval.entity.UserAndTeam;
import com.energizor.restapi.approval.repository.UserAndTeamRepository;
import com.energizor.restapi.group.dto.*;
import com.energizor.restapi.group.entity.*;
import com.energizor.restapi.group.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupService {

    private final DeptAndTeamRepository deptAndTeamRepository;
    private final TeamAndUsersRepository teamAndUsersRepository;
    private final UserGroupRepository userGroupRepository;
    private final DeptGroupRepository deptGroupRepository;
    private final TeamGroupRepository teamGroupRepository;

    private final AllGroupRepository allGroupRepository;

    private final UserAndTeamRepository userAndTeamRepository;

    private final ModelMapper modelMapper;

    public GroupService(ModelMapper modelMapper
            , AllGroupRepository allGroupRepository
            , DeptAndTeamRepository deptAndTeamRepository
            , DeptGroupRepository deptGroupRepository
            , TeamGroupRepository teamGroupRepository
            , TeamAndUsersRepository teamAndUsersRepository
            , UserGroupRepository userGroupRepository, UserAndTeamRepository userAndTeamRepository) {
        this.allGroupRepository = allGroupRepository;
        this.deptAndTeamRepository = deptAndTeamRepository;
        this.deptGroupRepository = deptGroupRepository;
        this.teamGroupRepository = teamGroupRepository;
        this.teamAndUsersRepository = teamAndUsersRepository;
        this.userGroupRepository = userGroupRepository;
        this.modelMapper = modelMapper;

        this.userAndTeamRepository = userAndTeamRepository;
    }


    public List<AllGroupDTO> selectAllGroupList() {

        log.info("selectAllGroup start=============");

        List<AllGroupList> groupList = allGroupRepository.findAll();

        log.info("selectAllGroup End===============");

        return groupList.stream()
                        .map(group -> modelMapper.map(group, AllGroupDTO.class))
                        .collect(Collectors.toList());

    }


    public DeptAndTeamDTO selectDeptAndTeam(int deptCode) {

        log.info("selectdepts start=============");

        DeptAndTeam dept = deptAndTeamRepository.findById(deptCode).get();
        DeptAndTeamDTO deptAndTeamDTO = modelMapper.map(dept, DeptAndTeamDTO.class);


        System.out.println("group ================ " + dept);

        log.info("selectdepts End===============");

        return deptAndTeamDTO;
    }


    public TeamAndUsersDTO selectTeamAndUsers(int teamCode) {

        log.info("selectTeam start=============");

        TeamAndUsers team = teamAndUsersRepository.findById(teamCode).get();
        TeamAndUsersDTO teamAndUsersDTO = modelMapper.map(team, TeamAndUsersDTO.class);


        System.out.println("group ================ " + team);

        log.info("selectTeam End===============");

        return teamAndUsersDTO;

    }

    public UserAndTeamDTO selectUsers(int userCode) {
        log.info("selectTeam start=============");

        UserAndTeam user = userAndTeamRepository.findByUserCode(userCode);
        UserAndTeamDTO userAndTeamDTO  = modelMapper.map(user, UserAndTeamDTO.class);


        log.info("selectTeam End===============");

        return userAndTeamDTO;
    }

    /* 부서생성 */

    @Transactional
    public String insertDept(DeptGroupDTO deptGroupDTO) {
        log.info("insertDept start ===============");
        log.info("insertDept start ==============="+ deptGroupDTO);

        int result = 0;

        try {
            DeptGroup insertDept = modelMapper.map(deptGroupDTO, DeptGroup.class);


            deptGroupRepository.save(insertDept);

            result = 1;
        } catch (Exception e) {
            System.out.println("check");
            throw new RuntimeException(e);
        }

        log.info("insertDept End=============");
        return (result > 0? "부서 생성 성공": "부서 생성 실패");

    }

    /* 팀생성 */

    @Transactional
    public String insertTeam(TeamGroupDTO teamGroupDTO) {

        log.info("insertTeam start ===============");
        log.info("insertTeam start ==============="+ teamGroupDTO);

        int result = 0;

        try {
            TeamGroup insertTeam = modelMapper.map(teamGroupDTO, TeamGroup.class);


            teamGroupRepository.save(insertTeam);

            result = 1;
        } catch (Exception e) {
            System.out.println("check");
            throw new RuntimeException(e);
        }

        log.info("insertTeam End=============");
        return (result > 0? "팀 생성 성공": "팀 생성 실패");

    }



    /* 부서 수정 */
    @Transactional
    public String updateDept(DeptGroupDTO deptGroupDTO) {

        log.info("updateDept start ===============");
        log.info("updateDept start ==============="+ deptGroupDTO);

        int result = 0;

        try {

            DeptGroup dept = deptGroupRepository.findById(deptGroupDTO.getDeptCode()).get();

            dept = dept.deptCode((long) deptGroupDTO.getDeptCode())
                    .deptName(deptGroupDTO.getDeptName()).build();

            result = 1;

        } catch (Exception e) {
            log.info("[updateDept] Exception!!");
            throw new RuntimeException(e);

        }
        log.info("[DeptService] updateDept End ===================================");
        return (result > 0) ? "부서 수정 성공": "부서 수정 실패";
    }

    @Transactional
    public String updateTeam(TeamGroupDTO teamGroupDTO) {

        log.info("updateTeam start ===============");
        log.info("updateTeam start ==============="+ teamGroupDTO);

        int result = 0;

        try {

            TeamGroup team = teamGroupRepository.findById(teamGroupDTO.getTeamCode()).get();

            team = team.teamCode((long) teamGroupDTO.getTeamCode())
                    .teamName(teamGroupDTO.getTeamName()).build();

            result = 1;
        } catch (Exception e) {
            log.info("[updateTeam] Exception!!");
            throw new RuntimeException(e);
        }
        log.info("[TeamService] updateTeam End ===================================");
        return (result > 0) ? "팀 수정 성공": "팀 수정 실패";
    }

    @Transactional
    public String deleteDept(DeptGroupDTO deptGroupDTO) {

        log.info("deleteDept start ==============="+ deptGroupDTO);

        int result = 0;

        try {
            DeptGroup dept = deptGroupRepository.findById(deptGroupDTO.getDeptCode()).get();

            deptGroupRepository.delete(dept);

            result = 1;

        }catch (Exception e) {
            log.info("[deleteDept] Exception!!");
            throw new RuntimeException(e);
        }
        log.info("[DeptService] deleteDept End ===================================");
        return (result > 0) ? "부서 삭제 성공": "부서 삭제 실패";

    }

    @Transactional
    public String deleteTeam(TeamGroupDTO teamGroupDTO) {

        log.info("deleteTeam start ==============="+ teamGroupDTO);

        int result = 0;

        try {
            TeamGroup team = teamGroupRepository.findById(teamGroupDTO.getTeamCode()).get();

            teamGroupRepository.delete(team);

            result = 1;
        } catch (Exception e) {
            log.info("[deleteTeam] Exception!!");
            throw new RuntimeException(e);
        }
        log.info("[TeamService] deleteTeam End ===================================");
        return (result > 0) ? "팀 삭제 성공": "팀 삭제 실패";
    }

}
