package com.energizor.restapi.calendar.service;
import com.energizor.restapi.calendar.dto.CalendarAndParticipantDTO;
import com.energizor.restapi.calendar.dto.CalendarDTO;
import com.energizor.restapi.calendar.entity.Calendar;
import com.energizor.restapi.calendar.entity.CalendarParticipant;
import com.energizor.restapi.calendar.entity.CalendarParticipantPK;
import com.energizor.restapi.calendar.repository.CalendarParticipantRepository;
import com.energizor.restapi.calendar.repository.CalendarRepository;
import com.energizor.restapi.calendar.repository.ScheduleRepository;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final ScheduleRepository scheduleRepository;
    private final CalendarParticipantRepository calendarParticipantRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public CalendarService(CalendarRepository calendarRepository, ScheduleRepository scheduleRepository, CalendarParticipantRepository calendarParticipantRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.calendarRepository = calendarRepository;
        this.scheduleRepository = scheduleRepository;
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
        List<Calendar> calendars = calendarParticipants.stream()
                .map(participant -> participant.getCalParticipant().getCalNo())
                .map(calendarRepository::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        return calendars.stream()
                .map(calendar -> {
                    CalendarDTO dto = modelMapper.map(calendar, CalendarDTO.class);
                    // 공유 캘린더일 경우 참여자의 이름을 조회합니다.
                    if ("공유 캘린더".equals(calendar.getCalType())) {
                        List<String> participantNames = // 여기서 참여자 이름을 조회하는 로직을 구현합니다.
                                calendarParticipantRepository.findParticipantNamesByCalNo(calendar.getCalNo());
                        dto.setParticipantNames(participantNames);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }




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


//    캘린더 수정
@Transactional
public String updateCalendar(int calNo, CalendarAndParticipantDTO calendarDTO) {
    Calendar calendar = calendarRepository.findById(calNo).orElse(null);
    if (calendar == null) {
        return "캘린더가 존재하지 않습니다.";
    }


    if (calendarDTO.getCalColor() != null) {
        calendar.setCalColor(calendarDTO.getCalColor());
    }
    if (calendarDTO.getCalName() != null) {
        calendar.setCalName(calendarDTO.getCalName());
    }

    // 공유 캘린더일 경우 참가자 목록 수정
    if ("공유 캘린더".equals(calendar.getCalType()) && calendarDTO.getUserCodes() != null) {
        // 기존 참가자 목록 삭제
        calendarParticipantRepository.deleteByCalParticipant_CalNo(calNo);

        // 새로운 참가자 목록 추가
        for (Integer userCode : calendarDTO.getUserCodes()) {
            CalendarParticipantPK participantPK = new CalendarParticipantPK();
            participantPK.setCalNo(calNo);
            participantPK.setUserCode(userCode);

            CalendarParticipant participant = new CalendarParticipant();
            participant.setCalParticipant(participantPK);
            calendarParticipantRepository.save(participant);
        }
    }

    calendarRepository.save(calendar); // 변경된 엔티티 저장

    return "캘린더 업데이트 성공";
}

    public Calendar findCalendarEntity(int calNo) {

        return calendarRepository.findById(calNo).orElse(null);
    }



    // 캘린더 삭제
    @Transactional
    public boolean deleteCalendar(int calNo, UserDTO principal) {
        // 캘린더 존재 확인
        Calendar calendar = findCalendarEntity(calNo);
        if (calendar == null) {
            return false;
        }

        try {
            // 해당 캘린더와 연결된 일정 삭제
            scheduleRepository.deleteByCalNo(calNo);

            // 해당 캘린더와 연결된 참가자 정보 삭제
            calendarParticipantRepository.deleteByCalParticipant_CalNo(calNo);

            // 캘린더 삭제
            calendarRepository.delete(calendar);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}