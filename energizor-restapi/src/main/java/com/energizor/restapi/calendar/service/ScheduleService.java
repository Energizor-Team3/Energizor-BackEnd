package com.energizor.restapi.calendar.service;

import com.energizor.restapi.calendar.dto.ScheduleDTO;
import com.energizor.restapi.calendar.entity.Schedule;
import com.energizor.restapi.calendar.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    // 캘린더 코드로 해당 캘린더 일정 조회

    public List<ScheduleDTO> findSchedulesByCalNo(int calNo) {
        List<Schedule> schedules = scheduleRepository.findByCalendar_CalNo(calNo);
        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }}