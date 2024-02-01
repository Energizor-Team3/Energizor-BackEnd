package com.energizor.restapi.calendar.controller;


import com.energizor.restapi.calendar.entity.Schedule;
import com.energizor.restapi.calendar.service.CalendarService;
import com.energizor.restapi.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {


    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
    @GetMapping("/calendar/{cal_no}")
    public ResponseEntity<ResponseDTO> getCalendar(@PathVariable int cal_no) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"캘린더 정보 조회 성공",calendarService.findCalendar(cal_no)));
    }

    @GetMapping("/calendars")
    public ResponseEntity<ResponseDTO> getAllCalendars() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "모든 캘린더 정보 조회 성공", calendarService.findAllCalendars()));
    }
    @GetMapping("/calendarByType/{calType}")
    public ResponseEntity<ResponseDTO> getCalendarsByType(@PathVariable String calType) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 정보 조회 성공", calendarService.findCalendarsByType(calType)));
    }
}