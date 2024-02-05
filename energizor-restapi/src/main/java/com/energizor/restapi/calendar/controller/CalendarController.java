package com.energizor.restapi.calendar.controller;


import com.energizor.restapi.calendar.dto.CalendarAndParticipantDTO;
import com.energizor.restapi.calendar.dto.CalendarDTO;
import com.energizor.restapi.calendar.entity.Calendar;
import com.energizor.restapi.calendar.service.CalendarService;
import com.energizor.restapi.calendar.service.ScheduleService;
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
    private final ScheduleService scheduleService;

    public CalendarController(CalendarService calendarService, ScheduleService scheduleService) {
        this.calendarService = calendarService;
        this.scheduleService = scheduleService;
    }


//----------------------------------------** 캘린더 **-----------------------------------------------------
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

// 유저 코드로 캘린더 조회
    @GetMapping("/calendarsByUserCode/{userCode}")
    public ResponseEntity<ResponseDTO> getCalendarsByUserCode(@PathVariable int userCode) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "유저코드로 캘린더 조회 성공", calendarService.findCalendarsByUserCode(userCode)));
    }

// 캘린더 추가
@PostMapping("/addCalendar")
public ResponseEntity<ResponseDTO> addNewCalendar(@RequestBody CalendarAndParticipantDTO calendarAndParticipantDTO){
    // 캘린더 및 참석자 정보를 함께 저장
    String result = calendarService.addNewCalendar(calendarAndParticipantDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK,result,null));
}

// 캘린더 수정
@PatchMapping("/updateCalendar/{calNo}")
public ResponseEntity<ResponseDTO> updateCalendar(@PathVariable int calNo, @RequestBody CalendarAndParticipantDTO calendarAndParticipantDTO) {
    Calendar existingCalendar = calendarService.findCalendarEntity(calNo);

    if (existingCalendar == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "캘린더를 찾을 수 없습니다", null));
    }

    calendarService.updateCalendar(existingCalendar, calendarAndParticipantDTO);

    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 수정 성공", null));
}


// 캘린더 삭제
@DeleteMapping("/deleteCalendar/{calNo}")
public ResponseEntity<ResponseDTO> deleteCalendar(@PathVariable int calNo) {
    // 캘린더 서비스를 통해 캘린더 삭제 요청
    boolean deleted = calendarService.deleteCalendar(calNo);
    if (deleted) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 삭제 성공", null));
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "캘린더를 찾을 수 없거나 삭제 실패", null));
    }
  }




//----------------------------------------** 스케줄 **-----------------------------------------------------




// 캘린더 코드로 해당 캘린더 일정 조회
    @GetMapping("/schedule/{calNo}")
    public ResponseEntity<ResponseDTO> getScheduleByCalendarCode(@PathVariable int calNo) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,  calNo + "번 캘린더의 스케줄 조회 성공", scheduleService.findSchedulesByCalNo(calNo)));
    }


//    @GetMapping("/schedule/{calNo}")
//    public ResponseEntity<ResponseDTO> getScheduleByCalendarCode(@PathVariable int calNo) {
//        CalendarDTO calendar = calendarService.findCalendar(calNo);
//        String calName = calendar != null ? calendar.getCalName() : "Unknown";
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, calNo+ "번캘린더" + calName + " 캘린더의 스케줄 조회 성공", scheduleService.findSchedulesByCalNo(calNo)));
//    }

}