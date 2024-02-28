package com.energizor.restapi.attendance.controller;

import com.energizor.restapi.attendance.entity.User;
import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.attendance.dto.CommuteDTO;
import com.energizor.restapi.attendance.service.AttendanceService;
import com.energizor.restapi.exception.NotFoundException;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@Log4j2
public class AttendanceController {

    private final AttendanceService attendanceService;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AttendanceController(AttendanceService attendanceService, UserRepository userRepository, ModelMapper modelMapper) {
        this.attendanceService = attendanceService;
        this.userRepository = userRepository;
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
    public ResponseEntity<ResponseDTO> startRegister(@RequestBody CommuteDTO commuteDTO, @AuthenticationPrincipal UserDTO user) {
        System.out.println("user = " + user);
        int userCode = commuteDTO.getUserCode();
        try {
             // 출근 등록 처리
            CommuteDTO startRegister = attendanceService.startRegister(userCode, commuteDTO);

            // 응답 생성
            ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "출근 등록 성공!", startRegister);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (NotFoundException ex) {
            // 사용자를 찾을 수 없는 경우 에러 응답 반환
            ResponseDTO responseDTO = new ResponseDTO(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    /* 퇴근 등록 */
    @PutMapping("/end-register/{cCode}")
    public ResponseEntity<ResponseDTO> endRegister(
            @PathVariable int cCode, @RequestBody CommuteDTO commuteDTO) {
        CommuteDTO endRegister = attendanceService.endRegister(cCode, commuteDTO);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "퇴근 등록 성공!", endRegister);
        System.out.println("responseDTO = " + responseDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
}