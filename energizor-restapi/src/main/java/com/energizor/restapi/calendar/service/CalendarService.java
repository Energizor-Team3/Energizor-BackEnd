package com.energizor.restapi.calendar.service;
import com.energizor.restapi.calendar.dto.CalendarAndParticipantDTO;
import com.energizor.restapi.calendar.dto.CalendarDTO;
import com.energizor.restapi.calendar.entity.Calendar;
import com.energizor.restapi.calendar.entity.CalendarParticipant;
import com.energizor.restapi.calendar.entity.CalendarParticipantPK;
import com.energizor.restapi.calendar.repository.CalendarParticipantRepository;
import com.energizor.restapi.calendar.repository.CalendarRepository;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarParticipantRepository calendarParticipantRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public CalendarService(CalendarRepository calendarRepository, CalendarParticipantRepository calendarParticipantRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.calendarRepository = calendarRepository;
        this.calendarParticipantRepository = calendarParticipantRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    //캘린더 코드로 조회
    public CalendarDTO findCalendar(int calNo){
        Calendar schedule = calendarRepository.findById(calNo).get();
        CalendarDTO calendarDTO = modelMapper.map(schedule, CalendarDTO.class);
        return calendarDTO;
    }

    //모든 캘린더 조회
    public List<CalendarDTO> findAllCalendars() {
        List<Calendar> calendars = calendarRepository.findAll();
        return calendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }


    // 캘린더 타입으로 조회
    public List<CalendarDTO> findCalendarsByType(String calType) {
        List<Calendar> calendarType = calendarRepository.findBycalType(calType);
        return calendarType.stream()
                .map(calendartype -> modelMapper.map(calendartype, CalendarDTO.class))
                .collect(Collectors.toList());
    }


    // 유저 코드로 캘린더 조회
    public List<CalendarDTO> findCalendarsByUserCode(int userCode) {
        List<CalendarParticipant> calendarParticipants = calendarParticipantRepository.findByCalParticipant_UserCode(userCode);
        List<Calendar> calendars = calendarParticipants.stream().map(participant -> participant.getCalParticipant().getCalNo()).map(calendarRepository::findById).flatMap(Optional::stream).collect(Collectors.toList());
        return calendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }


//    // 로그인한 유저 코드로 캘린더 추가
//    @Transactional
//    public String addNewCalendar(CalendarAndParticipantDTO  calendarAndParticipantDTO) {
//        Calendar calendar = new Calendar();
//        calendar.setCalType(calendarAndParticipantDTO.getCalType());
//        calendar.setCalColor(calendarAndParticipantDTO.getCalColor());
//        calendar.setCalName(calendarAndParticipantDTO.getCalName());
//        calendar = calendarRepository.save(calendar); // 캘린더 저장
//
//        // 캘린더에 참석자 정보 추가
//        CalendarParticipantPK participantPK = new CalendarParticipantPK();
//        participantPK.setCalNo(calendar.getCalNo()); // 새로 생성된 캘린더의 번호 설정
//        participantPK.setUserCode(calendarAndParticipantDTO.getUserCode()); // 사용자 코드 설정
//
//        CalendarParticipant participant = new CalendarParticipant();
//        participant.setCalParticipant(participantPK);
//        calendarParticipantRepository.save(participant); // 참석자 정보 저장
//
//        return "캘린더 추가 성공";
//    }

    public List<CalendarDTO> findCalendarsForLoggedInUser(UserDTO principal){
        System.out.println("<<<<<<<<<<principal>>>>>>>>>>"+principal);
        User user1 = userRepository.findByUserCode(principal.getUserCode());

        List<CalendarParticipant> calendarParticipants = calendarParticipantRepository.findByCalParticipant_UserCode(user1.getUserCode());
        List<Calendar> userCalendars = calendarParticipants.stream()
                .map(participant -> participant.getCalParticipant().getCalNo())
                .map(calendarRepository::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());


        return userCalendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());

    }

    @Transactional
    public String addNewCalendar(CalendarAndParticipantDTO calendarAndParticipantDTO, UserDTO principal) {

        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);

        User user1 = userRepository.findByUserCode(principal.getUserCode());
        User user = modelMapper.map(user1, User.class);

        Calendar calendar = new Calendar();
        calendar.setCalType(calendarAndParticipantDTO.getCalType());
        calendar.setCalColor(calendarAndParticipantDTO.getCalColor());
        calendar.setCalName(calendarAndParticipantDTO.getCalName());
        calendar = calendarRepository.save(calendar); // 캘린더 저장

        //캘린더에 참석자 추가
        //캘린더 타입이 개인 캘린더 일경우, 로그인 한 유저의 유저코드 를  calendar participant로 넣어줘야함
        //캘린더 타입이 공유 캘린더 일경우, 유저코드 한개 또는 여러개 넣을수 있음


        if ("개인 캘린더".equals(calendarAndParticipantDTO.getCalType())) {

            CalendarParticipantPK participantPK = new CalendarParticipantPK();
            participantPK.setCalNo(calendar.getCalNo()); // 새로 생성된 캘린더의 번호
            participantPK.setUserCode(user.getUserCode()); // 로그인 사용자의 코드 설정

            CalendarParticipant participant = new CalendarParticipant();
            participant.setCalParticipant(participantPK);
            calendarParticipantRepository.save(participant); // 참석자 정보 저장

        } else if ("공유 캘린더".equals(calendarAndParticipantDTO.getCalType())) {
            // 공유 캘린더인 경우 전달된 사용자 코드들로 설정
            List<Integer> userCodes = calendarAndParticipantDTO.getUserCodes();
            for (Integer userCode : userCodes) {
                CalendarParticipantPK participantPK = new CalendarParticipantPK();
                participantPK.setCalNo(calendar.getCalNo());
                participantPK.setUserCode(userCode); // 전달된 사용자 코드


                CalendarParticipant participant = new CalendarParticipant();
                participant.setCalParticipant(participantPK);
                calendarParticipantRepository.save(participant); // 참석자 저장
            }
        }

        return "캘린더 추가 성공";
    }
//개인 캘린더는-> 참석자 나만있음  -> 개인에서 공유로 변경 못함, 참석자도 추가못함
    //공유 캘린더는 -> 나 포함 여러명
    @Transactional
    public void updateCalendar(int calNo, CalendarAndParticipantDTO calendarAndParticipantDTO,UserDTO principal) {
        System.out.println("principal@@@@@@@@@@@@@@@@@@@@ = " + principal);

        User user1 = userRepository.findByUserCode(principal.getUserCode());
        User user = modelMapper.map(user1, User.class);

        // 캘린더가 존재하는지 확인
        Calendar calendar = findCalendarEntity(calNo);

        // 로그인한 사용자의 캘린더 목록 조회
        List<CalendarDTO> userCalendars = findCalendarsForLoggedInUser(principal);

        // 해당 사용자의 캘린더 목록에 해당하는 calNo가 있는지 확인
        boolean calendarExists = userCalendars.stream().anyMatch(cal -> cal.getCalNo() == calNo);
        if (calendar == null || !calendarExists) {
            return;
        }

        // 캘린더 이름 및 색상 업데이트
        if (calendarAndParticipantDTO.getCalName() != null) {
            calendar.setCalName(calendarAndParticipantDTO.getCalName());
        }
        if (calendarAndParticipantDTO.getCalColor() != null) {
            calendar.setCalColor(calendarAndParticipantDTO.getCalColor());
        }
        if (calendarAndParticipantDTO.getCalType() != null) {
            calendar.setCalType(calendarAndParticipantDTO.getCalType());
        }

        // 캘린더 저장
        calendarRepository.save(calendar);

        if ("개인 캘린더".equals(calendarAndParticipantDTO.getCalType())) {

            CalendarParticipantPK participantPK = new CalendarParticipantPK();
            participantPK.setUserCode(user.getUserCode()); // 로그인 사용자의 코드 설정
            CalendarParticipant participant = new CalendarParticipant();
            participant.setCalParticipant(participantPK);
            calendarParticipantRepository.save(participant); // 참석자 정보 저장

        } else if ("공유 캘린더".equals(calendarAndParticipantDTO.getCalType())) {
            // 공유 캘린더인 경우 전달된 사용자 코드들로 업데이트
            List<Integer> userCodes = calendarAndParticipantDTO.getUserCodes();
            for (Integer userCode : userCodes) {
                CalendarParticipantPK participantPK = new CalendarParticipantPK();
                participantPK.setCalNo(calendar.getCalNo());
                participantPK.setUserCode(userCode); // 전달된 사용자 코드


                CalendarParticipant participant = new CalendarParticipant();
                participant.setCalParticipant(participantPK);
                calendarParticipantRepository.save(participant); // 참석자 저장
            }
        }

    }


//
//
//    @Transactional
//    public void updateCalendar(int calNo, CalendarAndParticipantDTO calendarAndParticipantDTO,UserDTO principal) {
//        // 로그인한 사용자의 캘린더 목록 조회
//        List<CalendarDTO> userCalendars = findCalendarsForLoggedInUser(principal);
//
//        // 해당 사용자의 캘린더 목록에 해당하는 calNo가 있는지 확인
//        boolean calendarExists = userCalendars.stream().anyMatch(cal -> cal.getCalNo() == calNo);
//
//        // 캘린더가 존재하는지 확인
//        Calendar calendar = findCalendarEntity(calNo);
//
//        // 캘린더가 존재하지 않거나 해당 사용자의 캘린더 목록에 없으면 수정하지 않음
//        if (calendar == null || !calendarExists) {
//            return; // 캘린더가 존재하지 않거나 사용자의 캘린더 목록에 없으면 수정하지 않음
//        }
//
//        if (calendarAndParticipantDTO.getCalType() != null) {
//            calendar.setCalType(calendarAndParticipantDTO.getCalType());
//        }
//        if (calendarAndParticipantDTO.getCalColor() != null) {
//            calendar.setCalColor(calendarAndParticipantDTO.getCalColor());
//        }
//        if (calendarAndParticipantDTO.getCalName() != null) {
//            calendar.setCalName(calendarAndParticipantDTO.getCalName());
//        }
//
//        calendarRepository.save(calendar); // 수정된 캘린더 저장
//
//        if (calendarAndParticipantDTO.getUserCode() != null) {
//            updateParticipantInCalendar(calendar, calendarAndParticipantDTO.getUserCode());
//        }
//
//        if (calendarAndParticipantDTO.getUserCodes() != null && !calendarAndParticipantDTO.getUserCodes().isEmpty()) {
//            updateParticipantInCalendar(calendar, calendarAndParticipantDTO.getUserCodes());
//        }
//    }
//
//    @Transactional
//    public void updateParticipantInCalendar(Calendar calendar, List<Integer> newUserCodes) {
//        // 기존 참석자 정보 삭제
//        calendarParticipantRepository.deleteByCalParticipant_CalNo(calendar.getCalNo());
//
//        // 새로운 사용자 코드로 참석자 정보 추가
//        for (Integer newUserCode : newUserCodes) {
//            CalendarParticipantPK participantPK = new CalendarParticipantPK();
//            participantPK.setCalNo(calendar.getCalNo());
//            participantPK.setUserCode(newUserCode);
//
//            CalendarParticipant participant = new CalendarParticipant();
//            participant.setCalParticipant(participantPK);
//            calendarParticipantRepository.save(participant);
//        }
//    }
//
//    // 사용자 코드를 단일로 업데이트하는 메서드
//    @Transactional
//    public void updateParticipantInCalendar(Calendar calendar, Integer newUserCode) {
//        List<Integer> newUserCodes = Collections.singletonList(newUserCode);
//        updateParticipantInCalendar(calendar, newUserCodes);
//    }




    public Calendar findCalendarEntity(int calNo) {

        return calendarRepository.findById(calNo).orElse(null);
    }



    // 캘린더 삭제

    @Transactional
    public boolean deleteCalendar(int calNo, UserDTO principal) {
        // 캘린더가 존재하는지 확인
        Calendar calendar = findCalendarEntity(calNo);
        if (calendar == null) {
            return false; // 캘린더가 존재하지 않으면 삭제 실패
        }

        try {
            // 로그인한 사용자의 캘린더 목록 조회
            List<CalendarDTO> userCalendars = findCalendarsForLoggedInUser(principal);
            // 해당 사용자의 캘린더 목록에 해당하는 calNo가 있는지 확인
            boolean calendarExists = userCalendars.stream().anyMatch(cal -> cal.getCalNo() == calNo);
            if (!calendarExists) {
                return false; // 캘린더가 해당 사용자의 목록에 없으면 삭제 실패
            }

            // 관련된 참가자 정보 삭제
            calendarParticipantRepository.deleteByCalParticipant_CalNo(calNo);

            // 캘린더 삭제
            calendarRepository.delete(calendar);

            return true; // 삭제 성공
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 삭제 실패
        }
    }


}