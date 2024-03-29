package com.energizor.restapi.users.service;

import com.energizor.restapi.common.Criteria;
import com.energizor.restapi.users.dto.*;
import com.energizor.restapi.users.entity.*;
import com.energizor.restapi.users.repository.*;
import com.energizor.restapi.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final UserRoleRepository userRoleRepository;
    private final DayoffRepository dayoffRepository;

    private final TeamRepository teamRepository;

    private final DeptRepository deptRepository;
    private final AuthorityRepository authorityRepository;

    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    public UserService(UserRepository userRepository,
                       ModelMapper modelMapper,
                       UserRoleRepository userRoleRepository,
                       DayoffRepository dayoffRepository,
                       TeamRepository teamRepository,
                       DeptRepository deptRepository,
                       AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.dayoffRepository = dayoffRepository;
        this.teamRepository = teamRepository;
        this.deptRepository = deptRepository;
        this.authorityRepository=authorityRepository;
    }


    public Page<UserDTO> selectUserListWithPagingForAdmin(Criteria cri) {
        log.info("[UserService] selectUserListWithPagingForAdmin Start , Search: " + cri.getSearch());

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("userCode").descending());

//        Page<User> result = userRepository.findByUserStatus("Y", paging);
        Page<User> result;
        if (cri.getSearch() != null && !cri.getSearch().isEmpty()) {
            // 검색어가 있는 경우, 검색어를 포함하여 결과를 필터링합니다.
            result = userRepository.findByUserStatusAndUserNameContaining("Y", cri.getSearch(), paging);
        } else {
            // 검색어가 없는 경우, 모든 활성 사용자를 조회합니다.
            result = userRepository.findAll(paging);
        }

        Page<UserDTO> resultList = result.map(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);

            // Ensure teamDTO is not null
            if (user.getTeam() != null) {
                TeamDTO teamDTO = modelMapper.map(user.getTeam(), TeamDTO.class);

                // Ensure deptDTO is not null
                if (user.getTeam().getDept() != null) {
                    teamDTO.setDept(modelMapper.map(user.getTeam().getDept(), DeptDTO.class));
                }
                userDTO.setTeam(teamDTO);
            }
            return userDTO;
        });

        log.info("[UserService] selectUserListWithPagingForAdmin End ===================================");

        return resultList;
    }

    public UserDTO selectUserDetailForAdmin(int userCode) {
        log.info("[UserService] selectUserDetailForAdmin Start ===================================");

        User user = userRepository.findById(userCode).get();


        log.info("[UserService] selectUserDetailForAdmin End ===================================");



        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        System.out.println("userDTO =0000000000000000000000 " + userDTO);


        return userDTO;
    }

    @Transactional
    public String updateUserForAdmin(int userCode, UserDTO userDTO, UserDTO principal) {

        log.info("[UserService] updateUserForAdmin Start ===================================");
        log.info("[UserService] userCode : " + userCode);
        log.info("[UserService] userDTO : " + userDTO);
        log.info("[UserService] principal : " + principal);

        User user = userRepository.findById(userCode).orElseThrow(() -> new RuntimeException("해당 직원을 찾을 수 없습니다."));

        user.userCode(userCode);
        user.userId(userDTO.getUserId());

        if (userDTO.getUserPw() != null) {
            user.userPw(userDTO.getUserPw());
        }

        if (userDTO.getUserName() != null) {
            user.userName(userDTO.getUserName());
        }

        if (userDTO.getUserRank() != null) {
            user.userRank(userDTO.getUserRank());
        }

        if (userDTO.getEntDate() != null) {
            user.entDate(userDTO.getEntDate());
        }

        if (userDTO.getEmail() != null) {
            user.email(userDTO.getEmail());
        }

        if (userDTO.getPhone() != null) {
            user.phone(userDTO.getPhone());
        }

        if (userDTO.getResignDate() != null) {
            user.resignDate(userDTO.getResignDate());

            Instant instant = userDTO.getResignDate().toInstant();
            LocalDate resignDate = instant.atZone(ZoneId.of("UTC")).toLocalDate();

            if (resignDate.equals(LocalDate.of(9999, 12, 31))) {
                user.userStatus("Y");
            } else {
                user.userStatus("N");
            }
        }

        // UserRole 업데이트
        if (userDTO.isAdminRole() == true) {
            addRoleAdmin(user, userDTO);
        }
        if (userDTO.isAdminRole() == false) {
            deleteRoleAdmin(user, userDTO);
        }



        // TeamDTO를 이용한 업데이트
        if (userDTO.getTeam() != null) {
            TeamDTO teamDTO = userDTO.getTeam();

            // 데이터베이스에서 기존 Team 찾기
            Team team = teamRepository.findById(teamDTO.getTeamCode())
                    .orElseThrow(() -> new RuntimeException("해당 팀을 찾을 수 없습니다."));

            user.team(team);
        }

        // Dayoff 업데이트!!!
        if (userDTO.getOffUsed() > 0) {
            // UserDTO에서 직접 받은 offUsed 값으로 Dayoff 정보 업데이트
            Dayoff existingDayoff = dayoffRepository.findByUser_UserCode(userCode)
                    .orElseThrow(() -> new RuntimeException("연차 정보를 찾을 수 없습니다."));

            // offUsed 값 업데이트
            existingDayoff.offUsed(userDTO.getOffUsed());

            // 변경된 정보 저장
            dayoffRepository.save(existingDayoff);

            user.dayoff(existingDayoff);
        }

        userRepository.save(user);
        log.info("After saving user: {}", user);
        System.out.println("After saving user =========================== " + user);

        return "직원 정보 업데이트 성공";
    }

    @Transactional
    public void addRoleAdmin(User user, UserDTO userDTO) {
        Authority adminAuthority = (Authority) authorityRepository.findByAuthName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN 권한을 찾을 수 없습니다."));

        // Check if the user already has the ROLE_ADMIN role
        UserRole adminRole = userRoleRepository.findByUserCodeAndAuthCode(user.getUserCode(), adminAuthority.getAuthCode());
        if (adminRole == null) {
            // The user does not have the ROLE_ADMIN, so add it
            UserRole newUserRole = new UserRole(user.getUserCode(), adminAuthority.getAuthCode());
            userRoleRepository.save(newUserRole);
        }
    }

    @Transactional
    public void deleteRoleAdmin(User user, UserDTO userDTO) {
        Authority adminAuthority = (Authority) authorityRepository.findByAuthName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN 권한을 찾을 수 없습니다."));

        // Check if the user has the ROLE_ADMIN role
        UserRole adminRole = userRoleRepository.findByUserCodeAndAuthCode(user.getUserCode(), adminAuthority.getAuthCode());
        if (adminRole != null) {
            // The user has the ROLE_ADMIN, so remove it
            userRoleRepository.delete(adminRole);
        }
    }


    public UserDTO selectMyInfo(String userId) {
        log.info("[UserService]  selectMyInfo   Start =============== ");

        User user = userRepository.findByUserId(userId);
        log.info("[UserService]  {} =============== ", user);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProfilePath(IMAGE_URL + userDTO.getProfilePath());
        if (user.getTeam() != null) {
            TeamDTO teamDTO = modelMapper.map(user.getTeam(), TeamDTO.class);
            userDTO.setTeam(teamDTO);

            if (user.getTeam().getDept() != null) {
                DeptDTO deptDTO = modelMapper.map(user.getTeam().getDept(), DeptDTO.class);
                teamDTO.setDept(deptDTO);
            }
        }

        log.info("[UserService]  selectMyInfo   End =============== ");
        return userDTO;
    }

    public boolean isCorrectPassword(int userCode, String providedPassword) {

        User user = userRepository.findById(Integer.valueOf(userCode)).orElse(null);

        if (user != null) {

            System.out.println("user 존재 확인 =========" + user.getUserName());
            System.out.println("user 존재 확인 =========" + user.getUserPw());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            System.out.println("사용자에게 받은 '현재 비밀번호' 입력값 : " + providedPassword);
            System.out.println("로그인한 사용자의 비밀번호 : " + user.getUserPw());

            return passwordEncoder.matches(providedPassword, user.getUserPw());
        }

        return false;
    }

    @Transactional
    public void changePassword(int userCode, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userCode);

        // Check if the user exists
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(newPassword);

            user.userPw(encodedPassword);

            userRepository.save(user);
        }
    }

    public List<TeamDTO> getAllTeams() {
        List<TeamDTO> teamList = teamRepository.findAll().stream()
                .map(team -> {
                    DeptDTO deptDTO = null;
                    if (team.getDept() != null) {
                        deptDTO = new DeptDTO(team.getDept().getDeptCode(), team.getDept().getDeptName());
                    }
                    return new TeamDTO(team.getTeamCode(), team.getTeamName(), deptDTO);
                })
                .collect(Collectors.toList());

        System.out.println("teamList = " + teamList);

        return teamList;
    }

    @Transactional
    public Object updateProfile(UserDTO principal, MultipartFile profilePath) {

        log.info("[ProductService] updateProfile Start ===================================");
        log.info("[ProductService] principal : " + principal);

        String replaceFileName = null;
        int result = 0;

        try {

            User user = userRepository.findByUserCode(principal.getUserCode());
            String oriImage = user.getProfilePath();
            log.info("[updateProfile] oriImage: " + oriImage);

            if (profilePath != null) {
                String imageName = UUID.randomUUID().toString().replace("-", "");
                System.out.println("imageName ===== " + imageName);
                replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, profilePath);
                log.info("[updateProfile] replaceFileName : " + replaceFileName);
                System.out.println("IMAGE_DIR = " + IMAGE_DIR);
                user = user.profilePath(replaceFileName).build();

                if (!"defaultprofile.png".equals(oriImage)) {
                    boolean isDelete = FileUploadUtils.deleteFile(IMAGE_DIR, oriImage);
                    log.info("[update] isDelete : " + isDelete);
                }

            } else {
                user = user.profilePath(oriImage).build();
            }

            result = 1;

        } catch (IOException e) {
            log.info("[updateProfile] Exception!!");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }

        log.info("[UserService] updateProfile End ===================================");
        return (result > 0) ? "프로필이미지 업데이트 성공" : "프로필이미지 업데이트 실패";
    }

    @Transactional
    public Object deleteProfile(UserDTO principal) {

        log.info("[ProductService] deleteProfile Start ===================================");
        log.info("[ProductService] principal : " + principal);

        int result = 0;

        User user = userRepository.findByUserCode(principal.getUserCode());
        String oriImage = user.getProfilePath();
        log.info("[updateProfile] oriImage: " + oriImage);

        user = user.profilePath("defaultprofile.png").build();
        userRepository.save(user);

        result = 1;

        log.info("[UserService] deleteProfile End ===================================");

        return (result > 0) ? "프로필이미지 삭제 성공" : "프로필이미지 삭제 실패";
    }
}
