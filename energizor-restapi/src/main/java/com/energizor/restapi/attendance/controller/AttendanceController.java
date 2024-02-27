package com.energizor.restapi.attendance.controller;

import com.energizor.restapi.attendance.entity.Commute;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.attendance.dto.CommuteDTO;
import com.energizor.restapi.attendance.service.AttendanceService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@Log4j2
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final ModelMapper modelMapper;

    public AttendanceController(AttendanceService attendanceService, ModelMapper modelMapper) {
        this.attendanceService = attendanceService;
        this.modelMapper = modelMapper;
    }

    /* 출퇴근 전직원 목록 조회 */
    @GetMapping("/all-users-list")
    public ResponseEntity<ResponseDTO> getAttendanceList() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "출퇴근 전직원 목록 조회 성공!", attendanceService.findAllUsersList()));
    }

    /* 출퇴근 직원 목록 조회 */
    @GetMapping("/user-list/{userCode}")
    public ResponseEntity<ResponseDTO> getAttendanceListByUserCode(@PathVariable("userCode") int userCode) {
        List<CommuteDTO> user = attendanceService.getAttendanceListByUserCode(userCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "출퇴근 직원 목록 조회 성공!", user));
    }

    /* 출근 등록 */
    @PostMapping("/start-register")
    public ResponseEntity<ResponseDTO> startRegister(@RequestBody CommuteDTO commuteDTO) {
        int userCode = commuteDTO.getUserCode();
        CommuteDTO startRegister = attendanceService.startRegister(userCode, commuteDTO);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "출근 등록 성공!", startRegister);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    /* 퇴근 등록 */
    @PutMapping("/end-register/{cCode}")
    public ResponseEntity<ResponseDTO> endRegister(@PathVariable int cCode, @RequestBody CommuteDTO commuteDTO) {
        CommuteDTO endRegister = attendanceService.endRegister(cCode, commuteDTO);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "퇴근 등록 성공!", endRegister);
        return ResponseEntity.ok().body(responseDTO);
    }
}