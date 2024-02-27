package com.energizor.restapi.calendar.controller;


import com.energizor.restapi.calendar.dto.CalendarAndParticipantDTO;
import com.energizor.restapi.calendar.dto.CalendarDTO;
import com.energizor.restapi.calendar.dto.ScheduleDTO;
import com.energizor.restapi.calendar.service.CalendarService;
import com.energizor.restapi.calendar.service.ScheduleService;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.users.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "캘린더 관련 스웨거 연동 ")
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
//    캘린더 정보 조회

    @Operation(summary = "캘린더 정보 조회 ", description = " 하나의 캘린더 정보를 조회한다 ")
    @GetMapping("/calendar/{calNo}")
    public ResponseEntity<ResponseDTO> getCalendar(@PathVariable int calNo) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 정보 조회 성공", calendarService.findCalendar(calNo)));
    }

    //    캘린더 전체 목록 조회
    @Operation(summary = "전체 캘린더 조회 ", description = " 모든 캘린더 정보를 조회한다 ")
    @GetMapping("/calendars")
    public ResponseEntity<ResponseDTO> getAllCalendars() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "모든 캘린더 정보 조회 성공", calendarService.findAllCalendars()));
    }

    //    캘린더 타입으로 조회
    @Operation(summary = "캘린더 타입으로 조회 ", description = " 캘린더 타입별로 조회를 한다  ")
    @GetMapping("/calendarByType/{calType}")
    public ResponseEntity<ResponseDTO> getCalendarsByType(@PathVariable String calType) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 정보 조회 성공", calendarService.findCalendarsByType(calType)));
    }

    // 유저 코드로 캘린더 조회
    @Operation(summary = "유저코드별 캘린더 조회  ", description = " 한 유저의 캘린더를 조회한다  ")
    @GetMapping("/calendarsByUserCode/{userCode}")
    public ResponseEntity<ResponseDTO> getCalendarsByUserCode(@PathVariable int userCode) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "유저코드로 캘린더 조회 성공", calendarService.findCalendarsByUserCode(userCode)));
    }


    // 로그인한 유저 코드로 캘린더 추가
    @Operation(summary = "캘린더 추가  ", description = " 로그인한 유저의 캘린더를 추가한다  ")
    @PostMapping("/addCalendar")
    public ResponseEntity<ResponseDTO> addNewCalendar(@RequestBody CalendarAndParticipantDTO calendarAndParticipantDTO,
                                                      @AuthenticationPrincipal UserDTO principal) {

        System.out.println("principal=============================================================== = " + principal);
        // 캘린더 및 참석자 정보를 함께 저장
        String result = calendarService.addNewCalendar(calendarAndParticipantDTO, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, result, null));
    }

    // 로그인한 유저 코드로 캘린더 조회
    @GetMapping("/calendarsForLoggedInUser")
    public ResponseEntity<ResponseDTO> getCalendarsForLoggedInUser(@AuthenticationPrincipal UserDTO principal) {
        System.out.println("principal=============================================================== = " + principal);
        List<CalendarDTO> userCalendars = calendarService.findCalendarsForLoggedInUser(principal);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "로그인한 유저의 캘린더 목록 조회 성공", userCalendars));
    }


    //캘린더 수정

    @Operation(summary = "캘린더 수정  ", description = " 캘린더를 수정 한다  ")
    @PatchMapping("/update/{calNo}")
    public ResponseEntity<ResponseDTO> updateCalendar(@PathVariable int calNo, @RequestBody CalendarAndParticipantDTO calendarDTO) {
        String result = calendarService.updateCalendar(calNo, calendarDTO);
        if ("캘린더 업데이트 성공".equals(result)) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 업데이트 성공", null));
        } else {
            // 오류 메시지
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, result, null));
        }
    }



// 캘린더 삭제


    @Operation(summary = "캘린더 삭제  ", description = " 로그인한 유저의 캘린더 하나를 삭제한다  ")
    @DeleteMapping("/deleteCalendar/{calNo}")
    public ResponseEntity<ResponseDTO> deleteCalendar(@PathVariable int calNo,
                                                      @AuthenticationPrincipal UserDTO principal) {
        // 캘린더 서비스를 통해 캘린더 삭제 요청
        boolean deleted = calendarService.deleteCalendar(calNo, principal);
        if (deleted) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "캘린더 삭제 성공", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "캘린더를 찾을 수 없거나 삭제 실패", null));
        }
    }


//----------------------------------------** 스케줄 **-----------------------------------------------------

    @Operation(summary = "스케줄 디테일 정보 조회 ", description = " 하나의 스케줄 정보를 조회한다 ")
    @GetMapping("/schedule/detail/{schNo}")
    public ResponseEntity<ResponseDTO> getSchedule(@PathVariable int schNo) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "스케줄 정보 조회 성공", scheduleService.findSchedule(schNo)));
    }

    // 유저 코드로 스케줄 조회
    @GetMapping("/scheduleByUserCode/{userCode}")
    public ResponseEntity<ResponseDTO> getScheduleByUserCode(@PathVariable int userCode) {
        List<ScheduleDTO> schedules = scheduleService.findSchedulesByParticipantUserCode(userCode);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, userCode + "의 캘린더의 스케줄 조회 성공", scheduleService.findSchedulesByParticipantUserCode(userCode)));
    }


    // 캘린더 코드로 해당 캘린더 일정 조회
    @Operation(summary = "캘린더 일정 조회  ", description = " 캘린더 코드 별 일정을 조회한다  ")
    @GetMapping("/schedule/{calNo}")
    public ResponseEntity<ResponseDTO> getScheduleByCalendarCode(@PathVariable int calNo) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, calNo + "번 캘린더의 스케줄 조회 성공", scheduleService.findSchedulesByCalNo(calNo)));
    }


    @Operation(summary = "일정 추가  ", description = " 일정을 추가 한다 ")
    @PostMapping("/schedule/insert")
    public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "스케줄 등록 성공", scheduleService.insertSchedule(scheduleDTO)));

//       한 유저의 유저코드로 스케줄을 조회


    }

    @Operation(summary = "일정 삭제  ", description = " 일정을 삭제한다 ")
    @DeleteMapping("/schedule/delete/{schNo}")
    public ResponseEntity<ResponseDTO> deleteSchedule(@PathVariable int schNo) {
        String result = scheduleService.deleteSchedule(schNo);
        if (result.equals("Schedule delete successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "스케줄 삭제 성공", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "존재하지 않는 스케줄 입니다", null));
        }
    }


    @Operation(summary = "일정 수정  ", description = " 일정을 수정 한다 ")
    @PatchMapping("/schedule/update/{schNo}")
    public ResponseEntity<ResponseDTO> updateSchedule(@PathVariable int schNo, @RequestBody ScheduleDTO updatedScheduleDTO) {
        try {
            ScheduleDTO updatedSchedule = scheduleService.updateSchedule(schNo, updatedScheduleDTO);
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "스케줄 업데이트 성공", updatedSchedule));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "존재하지 않는 스케줄입니다", null));
        }
    }

//    @GetMapping("/schedule/{calNo}")
//    public ResponseEntity<ResponseDTO> getScheduleByCalendarCode(@PathVariable int calNo) {
//        CalendarDTO calendar = calendarService.findCalendar(calNo);
//        String calName = calendar != null ? calendar.getCalName() : "Unknown";
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, calNo+ "번캘린더" + calName + " 캘린더의 스케줄 조회 성공", scheduleService.findSchedulesByCalNo(calNo)));
//    }


}