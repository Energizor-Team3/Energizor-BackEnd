package com.energizor.restapi.users.service;

import com.energizor.restapi.common.Criteria;
import com.energizor.restapi.users.dto.AuthorityDTO;
import com.energizor.restapi.users.dto.DayOffDTO;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.dto.UserRoleDTO;
import com.energizor.restapi.users.entity.Authority;
import com.energizor.restapi.users.entity.Dayoff;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.entity.UserRole;
import com.energizor.restapi.users.repository.DayoffRepository;
import com.energizor.restapi.users.repository.UserRepository;
import com.energizor.restapi.users.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final UserRoleRepository userRoleRepository;
    private final DayoffRepository dayoffRepository;

    public UserService(UserRepository userRepository,
                       ModelMapper modelMapper,
                       UserRoleRepository userRoleRepository,
                       DayoffRepository dayoffRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.dayoffRepository = dayoffRepository;
    }

    public Page<UserDTO> selectUserListWithPagingForAdmin(Criteria cri) {
        log.info("[UserService] selectUserListWithPagingForAdmin Start ===================================");

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("userCode").descending());

        Page<User> result = userRepository.findAll(paging);

        Page<UserDTO> resultList = result.map(user -> modelMapper.map(user, UserDTO.class));

//        for(int i = 0 ; i < resultList.toList().size() ; i++) {
//            resultList.toList().get(i).setProductImageUrl(IMAGE_URL + resultList.toList().get(i).getProductImageUrl());
//        }

        log.info("[UserService] selectUserListWithPagingForAdmin End ===================================");

        return resultList;
    }

    public UserDTO selectUserDetailForAdmin(int userCode) {
        log.info("[UserService] selectUserDetailForAdmin Start ===================================");

        User user = userRepository.findById(userCode).get();
//        product = product.productImageUrl(IMAGE_URL + product.getProductImageUrl()).build();

        log.info("[UserService] selectUserDetailForAdmin End ===================================");

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

    @Transactional
    public String updateUserForAdmin(UserDTO userDTO, UserDTO principal) {

        log.info("[UserService] updateUserForAdmin Start ===================================");
        log.info("[UserService] userDTO : " + userDTO);
        log.info("[UserService] principal : " + principal);

        User user = userRepository.findById(userDTO.getUserCode()).get();

        user.userCode(userDTO.getUserCode());
        user.userId(userDTO.getUserId());

        if (userDTO.getUserPw() != null) {
            user.userPw(userDTO.getUserPw());
        }

        if (userDTO.getUserName() != null) {
            user.userName(userDTO.getUserName());
        }

        if (userDTO.getTeamCode() != 0) {
            user.team(userDTO.getTeamCode());
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
            if (userDTO.getResignDate().equals(java.sql.Date.valueOf("9999-12-31"))) {
                user.userStatus("Y");
            } else {
                user.userStatus("N");
            }
        }

        // UserRole 업데이트
        if (userDTO.getUserRole() != null) {
            // 기존의 역할 삭제
            for (UserRoleDTO roleDTO : userDTO.getUserRole()) {
                deleteByUserCodeAndAuthCode(user.getUserCode(), roleDTO.getAuthCode());
            }

            // 새로운 역할 추가
            List<UserRole> userRoles = userDTO.getUserRole().stream()
                    .map(roleDTO -> {
                        AuthorityDTO authorityDTO = roleDTO.getAuthority();
                        Authority authority = new Authority(authorityDTO.getAuthCode(), authorityDTO.getAuthName());
                        UserRole userRole = new UserRole(user.getUserCode(), roleDTO.getAuthCode(), authority);
                        return userRoleRepository.save(userRole);
                    })
                    .collect(Collectors.toList());
            user.userRole(userRoles);
        }

        // Dayoff 업데이트
        if (userDTO.getDayoff() != null) {
            DayOffDTO dayoffDTO = userDTO.getDayoff();
            Dayoff dayoff = new Dayoff()
                    .offYear(dayoffDTO.getOffYear())
                    .offCount(dayoffDTO.getOffCount())
                    .offUsed(dayoffDTO.getOffUsed())
                    .user(user);
            user.dayoff(dayoffRepository.save(dayoff));
        }

        userRepository.save(user);

        return (user != null)? "직원 정보 업데이트 성공" : "직원 정보 업데이트 실패";

//        User user = userRepository.findById(userDTO.getUserCode()).orElse(null);
//
//        if (user != null) {
//            // 엔터티 객체와 DTO 객체 간의 매핑
//            modelMapper.map(userDTO, user);
//            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
//
//            if (userDTO.getResignDate() != null) {
//                user.resignDate(userDTO.getResignDate());
//                if (userDTO.getResignDate().equals(java.sql.Date.valueOf("9999-12-31"))) {
//                    user.userStatus("Y");
//                } else {
//                    user.userStatus("N");
//                }
//            }

//            // UserRole 업데이트
//            if (userDTO.getUserRole() != null) {
//                // 기존의 역할 삭제
//                for (UserRoleDTO roleDTO : userDTO.getUserRole()) {
//                    deleteByUserCodeAndAuthCode(user.getUserCode(), roleDTO.getAuthCode());
//                }
//
//                // 새로운 역할 추가
//                List<UserRole> userRoles = userDTO.getUserRole().stream()
//                        .map(roleDTO -> {
//                            AuthorityDTO authorityDTO = roleDTO.getAuthority();
//                            Authority authority = new Authority(authorityDTO.getAuthCode(), authorityDTO.getAuthName());
//                            UserRole userRole = new UserRole(user.getUserCode(), roleDTO.getAuthCode(), authority);
//                            return userRoleRepository.save(userRole);
//                        })
//                        .collect(Collectors.toList());
//                user.userRole(userRoles);
//            }
//
//            // Dayoff 업데이트
//            if (userDTO.getDayoff() != null) {
//                DayOffDTO dayoffDTO = userDTO.getDayoff();
//                Dayoff dayoff = new Dayoff()
//                        .offYear(dayoffDTO.getOffYear())
//                        .offCount(dayoffDTO.getOffCount())
//                        .offUsed(dayoffDTO.getOffUsed())
//                        .user(user);
//                user.dayoff(dayoffRepository.save(dayoff));
//            }
//
//            userRepository.save(user);
//
//            return "직원 정보 업데이트 성공";
//        } else {
//            return "직원 정보 업데이트 실패";
//        }
    }


    public Object selectMyInfo(String userId) {
        log.info("[UserService]  selectMyInfo   Start =============== ");

        User user = userRepository.findByUserId(userId);
        log.info("[UserService]  {} =============== ", user);
        log.info("[UserService]  selectMyInfo   End =============== ");
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public void deleteByUserCodeAndAuthCode(int userCode, int authCode) {
        userRoleRepository.deleteByUserCodeAndAuthCode(userCode, authCode);
    }
}
