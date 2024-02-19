package com.energizor.restapi.users.service;

import com.energizor.restapi.exception.DuplicatedMemberEmailException;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.Dayoff;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.entity.UserRole;
import com.energizor.restapi.users.repository.DayoffRepository;
import com.energizor.restapi.users.repository.UserRepository;
import com.energizor.restapi.users.repository.UserRoleRepository;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final DayoffRepository dayoffRepository;

    public AuthService(UserRepository userRepository
                        , PasswordEncoder passwordEncoder
                        , ModelMapper modelMapper
                        , UserRoleRepository userRoleRepository
                        , DayoffRepository dayoffRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.dayoffRepository = dayoffRepository;
    }

    @Transactional   // DML작업은 Transactional 어노테이션 추가
    public Object signup(UserDTO userDTO) {

        log.info("[AuthService] signup Start ==================================");
        log.info("[AuthService] userDTO {} =======> ", userDTO);

        /* 이메일 중복 유효성 검사(선택적) */
        if(userRepository.findByEmail(userDTO.getEmail()) != null){ // 중복된 내용이 있으니 값을 가지고 온 것 (없으면 null)
            log.info("[AuthService] 이메일이 중복됩니다.");
            throw new DuplicatedMemberEmailException("이메일이 중복됩니다.");
        }

        User registUser = modelMapper.map(userDTO, User.class);

        /* 1. users 테이블에 회원 insert */
        // 입사일을 기반으로 하는 userId 생성
        Date entDate = registUser.getEntDate();
        Instant instant = entDate.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zone).toLocalDate();
        String baseUserId = DateTimeFormatter.ofPattern("yyMMdd").format(localDate);
        generateUserId(registUser, baseUserId);
        log.info("-------->>>>>>>>>>", userDTO.getUserId());

        // 존재하는 userId인지 확인
        while (userRepository.existsByUserId(registUser.getUserId())) {
            // 이미 존재하면 새로운 userId 생성
            generateUserId(registUser, baseUserId);
        }
        log.info(">>>>>>>>>>>>>>>>", userDTO.getUserId());

        registUser = registUser.userPw(passwordEncoder.encode(registUser.getUserPw())).build(); // 평문의 암호문자열을 암호화시켜서 전달
        User result1 = userRepository.save(registUser);    // 반환형이 int값이 아니다.
        log.info("[AuthService] result1 ================== {} ", result1);

        /* 2. user_role 테이블에 회원별 권한 insert (현재 엔티티에는 회원가입 후 pk값이 없는상태) */
        // 일반 권한의 회원을 추가(AuthorityCode값이 1번)

        /* 엔티티에는 추가할 회원의 pk값이 아직 없으므로 기존 회원의 마지막 회원 번호를 조회
        *  하지만 jpql에 의해 앞선 save와 jpql이 flush()로 쿼리와 함께 날아가고 회원이 이미 sequence객체 값 증가와 함께
        *  insert가 되어 버린다. -> 고로, maxUserCode가 현재 가입하는 회원의 번호를 의미한다.
        * */
        UserRole registUserRole = new UserRole(result1.getUserCode(), 1);
        UserRole result2 = userRoleRepository.save(registUserRole);
        log.info("[AuthService] result2 ================== {} ", result2);

        /* 연차 insert */
        Dayoff registDayoff = new Dayoff();
        registDayoff.offYear(Year.of(LocalDate.now().getYear()));
        long monthsWorked = ChronoUnit.MONTHS.between(
                result1.getEntDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now().withMonth(12).withDayOfMonth(31));
        registDayoff.offCount((int) Math.floor(monthsWorked));
        registDayoff.offUsed(0);
        registDayoff.user(result1);
        log.info("[AuthService] registDayoff ================== {} ", registDayoff);
        Dayoff result3 = dayoffRepository.save(registDayoff);

        // result3의 offCode를 읽어와서 User 엔티티의 dayoff에 설정
        result1.dayoff(result3);

        log.info("[AuthService] UserInsert Result {}",
                        (result1 != null && result2 != null && result3 != null)? "회원 가입 성공" : "회원 가입 실패");

        log.info("[AuthService] signup End ==================================");




        return userDTO;
    }

    private void generateUserId(User user, String baseUserId) {
        int sequenceNumber = 1; // 초기값
        String generatedUserId = baseUserId + String.format("%03d", sequenceNumber);

        while (userRepository.existsByUserId(generatedUserId)) {
            sequenceNumber++;
            generatedUserId = baseUserId + String.format("%03d", sequenceNumber);
            System.out.println("sequenceNumber = " + sequenceNumber);
            System.out.println(generatedUserId);
        }

        user.userId(generatedUserId);
    }

}
