package com.energizor.restapi.calendar.service;

import com.energizor.restapi.calendar.dto.CalendarDTO;
import com.energizor.restapi.calendar.dto.ScheduleAndCalendarDTO;
import com.energizor.restapi.calendar.dto.ScheduleDTO;
import com.energizor.restapi.calendar.entity.Calendar;
import com.energizor.restapi.calendar.entity.Schedule;
import com.energizor.restapi.calendar.entity.ScheduleAndCalendar;
import com.energizor.restapi.calendar.repository.CalendarParticipantRepository;
import com.energizor.restapi.calendar.repository.CalendarRepository;
import com.energizor.restapi.calendar.repository.ScheduleAndCategoryRepository;
import com.energizor.restapi.calendar.repository.ScheduleRepository;
import com.energizor.restapi.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;



@Service
@Log4j2
public class ScheduleService {

    private final ScheduleAndCategoryRepository scheduleAndCategoryRepository;


    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;


    public ScheduleService(ScheduleAndCategoryRepository scheduleAndCategoryRepository, CalendarRepository calendarRepository, CalendarParticipantRepository calendarParticipantRepository, ScheduleRepository scheduleRepository, ModelMapper modelMapper, UserRepository userRepository, CalendarService calendarService) {
        this.scheduleAndCategoryRepository = scheduleAndCategoryRepository;
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;

    }
//일정 하나 조회
public ScheduleDTO findSchedule(int schNo) {
    Optional<Schedule> optionalSchedule = scheduleRepository.findById(schNo);
    if (optionalSchedule.isPresent()) {
        Schedule schedule = optionalSchedule.get();
        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
        return scheduleDTO;
    } else {

        return null;
    }
}


    // 캘린더 코드로 해당 캘린더 일정 조회

    public List<ScheduleAndCalendarDTO> findSchedulesByCalNo(int calNo) {
        List<ScheduleAndCalendar> schedules = scheduleAndCategoryRepository.findByCalendar_CalNo(calNo);
        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleAndCalendarDTO.class))
                .collect(Collectors.toList());
    }
    public List<ScheduleDTO> findSchedulesByParticipantUserCode(int userCode) {
        return scheduleRepository.findScheduleAndColorByUserCode(userCode)
                .stream()
                .map(result -> {
                    Schedule schedule = (Schedule) result[0];
                    String calColor = (String) result[1];
                    ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
                    scheduleDTO.setCalColor(calColor);
                    return scheduleDTO;
                })
                .collect(Collectors.toList());
    }
//    public List<ScheduleDTO> findSchedulesByParticipantUserCode(int userCode) {
//        return scheduleRepository.findScheduleByUserCode(userCode)
//                .stream()
//                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
//                .collect(Collectors.toList());
//    }

    @Transactional

    public ScheduleDTO insertSchedule(ScheduleDTO scheduleDTO) {
        log.info("[Schedule Service] Insert start===============");
        if(scheduleDTO.getSchAllDay() == null) {
            scheduleDTO.setSchAllDay("N");
        }

        Schedule insertSchedule=modelMapper.map(scheduleDTO, Schedule.class);
        Schedule savedSchedule=scheduleRepository.save(insertSchedule);

        ScheduleDTO savedScheduleDTO=modelMapper.map(savedSchedule, ScheduleDTO.class);

        log.info("[BoardService] register end ==================");
        return savedScheduleDTO;
    }

    @Transactional
    public String  deleteSchedule(int schNo) {
        log.info("[Schedule Service] delete  Start ===================================");
        log.info("[Schedule Service] delete : " + schNo);

        Optional<Schedule> optionalSchedule = scheduleRepository.findById(schNo);
        if (optionalSchedule.isPresent()) {
            scheduleRepository.delete(optionalSchedule.get());
            return "Schedule delete successfully";
        } else {
            return "Schedule not found";
        }
    }





    @Transactional
    public ScheduleDTO updateSchedule(int schNo, ScheduleDTO updatedScheduleDTO) {
        log.info("[Schedule Service] Update start===============");

        Optional<Schedule> optionalSchedule = scheduleRepository.findById(schNo);
        if (optionalSchedule.isPresent()) {
            Schedule existingSchedule = optionalSchedule.get();

            if (updatedScheduleDTO.getSchTitle() != null) {
                existingSchedule.setSchTitle(updatedScheduleDTO.getSchTitle());
            }
            if (updatedScheduleDTO.getSchDetail() != null) {
                existingSchedule.setSchDetail(updatedScheduleDTO.getSchDetail());
            }
            if (updatedScheduleDTO.getSchStartDate() != null) {
                existingSchedule.setSchStartDate(updatedScheduleDTO.getSchStartDate());
            }
            if (updatedScheduleDTO.getSchEndDate() != null) {
                existingSchedule.setSchEndDate(updatedScheduleDTO.getSchEndDate());
            }
            if (updatedScheduleDTO.getSchAllDay() != null) {
                existingSchedule.setSchAllDay(updatedScheduleDTO.getSchAllDay());
            }
            if (updatedScheduleDTO.getSchLocal() != null) {
                existingSchedule.setSchLocal(updatedScheduleDTO.getSchLocal());
            }
            if (updatedScheduleDTO.getCalNo() != 0) {
                existingSchedule.setCalNo(updatedScheduleDTO.getCalNo());
            }

            // 변경된 스케줄을 저장하고, 저장된 DTO를 반환합니다.
            Schedule savedSchedule = scheduleRepository.save(existingSchedule);
            ScheduleDTO savedScheduleDTO = modelMapper.map(savedSchedule, ScheduleDTO.class);

            log.info("[Schedule Service] Update end ==================");
            return savedScheduleDTO;
        } else {
            throw new IllegalArgumentException("Schedule not found with schNo: " + schNo);
        }
    }

}





