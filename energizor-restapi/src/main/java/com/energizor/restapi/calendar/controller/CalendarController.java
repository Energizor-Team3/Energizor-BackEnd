package com.energizor.restapi.calendar.controller;


import com.energizor.restapi.calendar.dto.CalendarDTO;
import com.energizor.restapi.calendar.entity.Calendar;
import com.energizor.restapi.calendar.service.CalendarService;
import com.energizor.restapi.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {


    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

//    캘린더 목록 조회
    @GetMapping("/calendar/{calNo}")
    public ResponseEntity<ResponseDTO> getCalendar(@PathVariable int calNo) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"캘린더 정보 조회 성공",calendarService.findCalendar(calNo)));
    }

//    캘린더 전체 목록 조회
    @GetMapping("/calendars")
    public ResponseEntity<ResponseDTO> getAllCalendars() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "모든 캘린더 정보 조회 성공", calendarService.findAllCalendars()));
    }


//    캘린더 타입으로 조회

    @GetMapping("/calendarByType/{calType}")
    public ResponseEntity<ResponseDTO> getCalendarsByType(@PathVariable String calType) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 정보 조회 성공", calendarService.findCalendarsByType(calType)));
    }


    @GetMapping("/calendarsByUserCode/{userCode}")
    public ResponseEntity<ResponseDTO> getCalendarsByUserCode(@PathVariable int userCode) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "유저코드로 캘린더 조회 성공", calendarService.findCalendarsByUserCode(userCode)));
    }

    @GetMapping("/calendarSetting")
    public void calendarSettingPage() {}



    @PostMapping("/addCalendar")
    public ResponseEntity<ResponseDTO> addNewCalendar(@RequestBody CalendarDTO calendarDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK,"캘린더 추가 성공",calendarService.addNewCalendar(calendarDTO)));
    }




    //  부분수정은 patch
    @PatchMapping("/updateCalendar/{calNo}")
    public ResponseEntity<ResponseDTO> updateCalendar(@PathVariable int calNo, @RequestBody CalendarDTO calendarDTO) {
        Calendar existingCalendar = calendarService.findCalendarEntity(calNo);

        if (existingCalendar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "캘린더를 찾을 수 없습니다", null));
        }

        if (calendarDTO.getCalType() != null) {
            existingCalendar.setCalType(calendarDTO.getCalType());
        }
        if (calendarDTO.getCalColor() != null) {
            existingCalendar.setCalColor(calendarDTO.getCalColor());
        }
        if (calendarDTO.getCalName() != null) {
            existingCalendar.setCalName(calendarDTO.getCalName());
        }


        // 수정된 캘린더 저장
        calendarService.updateCalendar(existingCalendar);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 수정 성공", null));
    }
}





