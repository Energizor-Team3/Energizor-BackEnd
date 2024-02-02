package com.energizor.restapi.calendar.service;

import com.energizor.restapi.calendar.dto.CalendarDTO;

import com.energizor.restapi.calendar.entity.Calendar;

import com.energizor.restapi.calendar.entity.CalendarParticipant;
import com.energizor.restapi.calendar.repository.CalendarParticipantRepository;
import com.energizor.restapi.calendar.repository.ScheduleRepository;
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

    private final ScheduleRepository scheduleRepository;
    private final CalendarParticipantRepository calendarParticipantRepository;
    private final ModelMapper modelMapper;

    public CalendarService(ScheduleRepository scheduleRepository, CalendarParticipantRepository calendarParticipantRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.calendarParticipantRepository = calendarParticipantRepository;
        this.modelMapper = modelMapper;
    }


    public CalendarDTO findCalendar(int calNo){
        Calendar schedule = scheduleRepository.findById(calNo).get();
        CalendarDTO calendarDTO = modelMapper.map(schedule, CalendarDTO.class);
        return calendarDTO;
    }

    public List<CalendarDTO> findAllCalendars() {
        List<Calendar> calendars = scheduleRepository.findAll();
        return calendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }


    public List<CalendarDTO> findCalendarsByType(String calType) {
        List<Calendar> calendarType = scheduleRepository.findBycalType(calType);
        return calendarType.stream()
                .map(calendartype -> modelMapper.map(calendartype, CalendarDTO.class))
                .collect(Collectors.toList());
    }

    public List<CalendarDTO> findCalendarsByUserCode(int userCode) {
        List<CalendarParticipant> calendarParticipants = calendarParticipantRepository.findByCalParticipant_UserCode(userCode);
        List<Calendar> calendars = calendarParticipants.stream().map(participant -> participant.getCalParticipant().getCalNo()).map(scheduleRepository::findById).flatMap(Optional::stream).collect(Collectors.toList());
        return calendars.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public String addNewCalendar(CalendarDTO calendarDTO) {
        Calendar calendar = modelMapper.map(calendarDTO,Calendar.class);
        scheduleRepository.save(calendar);
        return "캘린더 추가 성공";}

    @Transactional
    public String updateCalendar(Calendar calendar) {
        Calendar updatedCalendar = scheduleRepository.save(calendar);
        if (updatedCalendar != null) {
            return "캘린더 수정 성공";
        } else {
            return "캘린더 수정 실패";
        }
    }

    public Calendar findCalendarEntity(int calNo) {
        return scheduleRepository.findById(calNo).orElse(null);
    }

}