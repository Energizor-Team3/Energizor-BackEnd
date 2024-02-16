package com.energizor.restapi.users.service;

import com.energizor.restapi.exception.DuplicatedMemberEmailException;
import com.energizor.restapi.users.dto.MailDTO;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.Dayoff;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.entity.UserRole;
import com.energizor.restapi.users.repository.DayoffRepository;
import com.energizor.restapi.users.repository.UserRepository;
import com.energizor.restapi.users.repository.UserRoleRepository;

import com.energizor.restapi.util.TokenUtils;
import jakarta.mail.internet.InternetAddress;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final DayoffRepository dayoffRepository;

    private final JavaMailSender javaMailSender;

    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    public AuthService(UserRepository userRepository
                        , PasswordEncoder passwordEncoder
                        , ModelMapper modelMapper
                        , UserRoleRepository userRoleRepository
                        , DayoffRepository dayoffRepository
                        , JavaMailSender javaMailSender){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.dayoffRepository = dayoffRepository;
        this.javaMailSender = javaMailSender;
    }

    @Transactional
    public Object signup(UserDTO userDTO) {

        log.info("[AuthService] signup Start ==================================");
        log.info("[AuthService] userDTO {} =======> ", userDTO);

        /* 이메일 중복 유효성 검사(선택적) */
        if(userRepository.findByEmail(userDTO.getEmail()) != null){ // 중복된 내용이 있으니 값을 가지고 온 것 (없으면 null)
            log.info("[AuthService] 이메일이 중복됩니다.");
            throw new DuplicatedMemberEmailException("이메일이 중복됩니다.");
        }

        userDTO.setProfilePath("/defaultprofile.png");

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

    //보낼 사용자 이메일의 정보 내가 보내는거면 내 이메일
    private void sendPasswordSearchEmail(String email, String temporaryPassword) throws UnsupportedEncodingException {
        System.out.println("비번 변경 서비스 시작=====================================");
        System.out.println("사용자의 이메일 정보 =====================================" + email);

        SimpleMailMessage message = new SimpleMailMessage();
        InternetAddress fromAddress = new InternetAddress("sueyeon777@gmail.com", "EveryWare 인사담당자");
        message.setFrom(String.valueOf(fromAddress));
        message.setTo(email);
        message.setSubject("EveryWare 임시비밀번호 안내");
        message.setText("안녕하세요. EveryWare 임시비밀번호 안내 관련 이메일 입니다." +
                " 회원님의 임시 비밀번호는 " + temporaryPassword + " 입니다." +
                "로그인 후에 비밀번호를 변경해주세요!");

        javaMailSender.send(message);
    }


    @Transactional
    public UserDTO sendSearchPwd(String userId, String email) throws UserPrincipalNotFoundException, UnsupportedEncodingException {
        Optional<User> user = userRepository.findByUserIdAndEmail(userId, email);
        System.out.println("비번 변경 서비스 시작=====================================");
        System.out.println("user 들어있는지 확인용 = " + user);
        if (user.isPresent()) {
            //여기서 임시 비밀번호를 생성함
//            String temporaryPassword = TokenUtils.randomString(); //토큰유틸에 만들어놓음
            String temporaryPassword = getTempPassword();
            System.out.println("temporaryPassword 임시비밀번호 출력확인용 = " + temporaryPassword);
            //이메일로 임시 비밀번호 전송
            sendPasswordSearchEmail(email, temporaryPassword);
            //사용자 비밀번호 업데이트
            System.out.println(" 비번 업데이트 시작=====================================");
//            LoginEmployee loginEmployee = employee.get();//get은 optional클래스에서 사용되는 메서드 중 하나
            System.out.println("passwordEncoder.encode(temporaryPassword)"+passwordEncoder.encode(temporaryPassword));
            //이게 출력이 안되었던 이유는 bean에 passencoder를 등록해줬어야 되었는데 안해줘서 값을 아예 못가져왔는데
            //위에서 autowired를 passencoder에 넣어주니까 값을 잘 들고왔다 메일은 보내졌는데 메일이 보내지고 나서 값을 잃었던 것

            System.out.println(" 비번 업데이트 시작에서 정보 가져오기=====================================");

//            loginEmployee.setEmployeePassword(passwordEncoder.encode(temporaryPassword)); //passwordEncoder 사용
            user.get().userPw(passwordEncoder.encode(temporaryPassword));  //엔티티로 해야됨
            System.out.println(" 비번 변경 서비스 거의 다옴 =====================================");

            userRepository.save(user.get());

            System.out.println(" 비번 변경 서비스 끝=====================================");

            return modelMapper.map(user.get(), UserDTO.class); //일단 오류는 안뜨게함
        } else {
            throw new UserPrincipalNotFoundException("사용자 정보를 찾을 수 없습니다.");
        }
    }

    // 임시 비밀번호 생성
    public static String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

}
