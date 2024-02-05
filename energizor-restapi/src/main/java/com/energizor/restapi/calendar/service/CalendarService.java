package com.energizor.restapi.calendar.service;

import com.energizor.restapi.calendar.dto.CalendarAndParticipantDTO;
import com.energizor.restapi.calendar.dto.CalendarDTO;

import com.energizor.restapi.calendar.entity.Calendar;

import com.energizor.restapi.calendar.entity.CalendarParticipant;
import com.energizor.restapi.calendar.entity.CalendarParticipantPK;
import com.energizor.restapi.calendar.repository.CalendarParticipantRepository;
import com.energizor.restapi.calendar.repository.CalendarRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public CalendarService(CalendarRepository calendarRepository, CalendarParticipantRepository calendarParticipantRepository, ModelMapper modelMapper) {
        this.calendarRepository = calendarRepository;
        this.calendarParticipantRepository = calendarParticipantRepository;
        this.modelMapper = modelMapper;
    }


    public CalendarDTO findCalendar(int calNo){
        Calendar schedule = calendarRepository.findById(calNo).get();
        CalendarDTO calendarDTO = modelMapper.map(schedule, CalendarDTO.class);
        return calendarDTO;
    }

    public List<CalendarDTO> findAllCalendars() {
        List<Calendar> calendars = calendarRepository.findAll();
        return calendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }


    public List<CalendarDTO> findCalendarsByType(String calType) {
        List<Calendar> calendarType = calendarRepository.findBycalType(calType);
        return calendarType.stream()
                .map(calendartype -> modelMapper.map(calendartype, CalendarDTO.class))
                .collect(Collectors.toList());
    }

    public List<CalendarDTO> findCalendarsByUserCode(int userCode) {
        List<CalendarParticipant> calendarParticipants = calendarParticipantRepository.findByCalParticipant_UserCode(userCode);
        List<Calendar> calendars = calendarParticipants.stream().map(participant -> participant.getCalParticipant().getCalNo()).map(calendarRepository::findById).flatMap(Optional::stream).collect(Collectors.toList());
        return calendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public String addNewCalendar(CalendarAndParticipantDTO  calendarAndParticipantDTO) {
        Calendar calendar = new Calendar();
        calendar.setCalType(calendarAndParticipantDTO.getCalType());
        calendar.setCalColor(calendarAndParticipantDTO.getCalColor());
        calendar.setCalName(calendarAndParticipantDTO.getCalName());
        calendar = calendarRepository.save(calendar); // 캘린더 저장

        // 캘린더에 참석자 정보 추가
        CalendarParticipantPK participantPK = new CalendarParticipantPK();
        participantPK.setCalNo(calendar.getCalNo()); // 새로 생성된 캘린더의 번호 설정
        participantPK.setUserCode(calendarAndParticipantDTO.getUserCode()); // 사용자 코드 설정

        CalendarParticipant participant = new CalendarParticipant();
        participant.setCalParticipant(participantPK);
        calendarParticipantRepository.save(participant); // 참석자 정보 저장

        return "캘린더 추가 성공";
    }
    @Transactional
    public void updateCalendar(Calendar calendar, CalendarAndParticipantDTO calendarAndParticipantDTO) {
        if (calendarAndParticipantDTO.getCalType() != null) {
            calendar.setCalType(calendarAndParticipantDTO.getCalType());
        }
        if (calendarAndParticipantDTO.getCalColor() != null) {
            calendar.setCalColor(calendarAndParticipantDTO.getCalColor());
        }
        if (calendarAndParticipantDTO.getCalName() != null) {
            calendar.setCalName(calendarAndParticipantDTO.getCalName());
        }

        calendarRepository.save(calendar);

        if (calendarAndParticipantDTO.getUserCode() != null) {
            updateParticipantInCalendar(calendar, calendarAndParticipantDTO.getUserCode());
        }

        if (calendarAndParticipantDTO.getUserCodes() != null && !calendarAndParticipantDTO.getUserCodes().isEmpty()) {
            updateParticipantInCalendar(calendar, calendarAndParticipantDTO.getUserCodes());
        }
    }

    @Transactional
    public void updateParticipantInCalendar(Calendar calendar, List<Integer> newUserCodes) {
        // 기존 참석자 정보 삭제
        calendarParticipantRepository.deleteByCalParticipant_CalNo(calendar.getCalNo());

        // 새로운 사용자 코드로 참석자 정보 추가
        for (Integer newUserCode : newUserCodes) {
            CalendarParticipantPK participantPK = new CalendarParticipantPK();
            participantPK.setCalNo(calendar.getCalNo());
            participantPK.setUserCode(newUserCode);

            CalendarParticipant participant = new CalendarParticipant();
            participant.setCalParticipant(participantPK);
            calendarParticipantRepository.save(participant);
        }
    }

    // 사용자 코드를 단일로 업데이트하는 메서드
    @Transactional
    public void updateParticipantInCalendar(Calendar calendar, Integer newUserCode) {
        List<Integer> newUserCodes = Collections.singletonList(newUserCode);
        updateParticipantInCalendar(calendar, newUserCodes);
    }




    public Calendar findCalendarEntity(int calNo) {

        return calendarRepository.findById(calNo).orElse(null);
    }

    @Transactional
    public boolean deleteCalendar(int calNo) {
        // 캘린더가 존재하는지 확인
        Calendar calendar = findCalendarEntity(calNo);
        if (calendar == null) {
            return false; // 캘린더가 존재하지 않으면 삭제 실패           ㅏ
        }

        try {
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