package com.energizor.restapi.calendar.service;

import com.energizor.restapi.calendar.dto.CalendarDTO;

import com.energizor.restapi.calendar.dto.ScheduleDTO;
import com.energizor.restapi.calendar.entity.Calendar;

import com.energizor.restapi.calendar.entity.Schedule;
import com.energizor.restapi.calendar.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CalendarService {

    private final ScheduleRepository scheduleRepository;

    private final ModelMapper modelMapper;

    public CalendarService(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }


    public CalendarDTO findCalendar(int cal_no){
        Calendar schedule = scheduleRepository.findById(cal_no).get();
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



}