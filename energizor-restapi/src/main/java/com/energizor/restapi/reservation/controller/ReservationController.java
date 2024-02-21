package com.energizor.restapi.reservation.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.reservation.dto.AttendeeDTO;
import com.energizor.restapi.reservation.dto.MeetingTimeDTO;
import com.energizor.restapi.reservation.dto.ReservationDTO;
import com.energizor.restapi.reservation.dto.ReservationTimeDTO;
import com.energizor.restapi.reservation.entity.Attendee;
import com.energizor.restapi.reservation.entity.Reservation;
import com.energizor.restapi.reservation.service.ReservationService;
import com.energizor.restapi.users.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "자원예약")
@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //예약내역 전체조회
    @Operation(summary = "내 예약 조회")
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> selectAllReservations(@AuthenticationPrincipal UserDTO userDTO){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 예약 내역 조회 성공",
                reservationService.selectAllReservations(userDTO)));
    }

    //예약내역 상세조회
    @Operation(summary = "내 예약 상세 조회")
    @GetMapping("/{reservation_code}")
    public ResponseEntity<ResponseDTO> selectReservationByCode(@PathVariable int reservation_code){
        ReservationDTO reservationDTO = reservationService.selectReservationByCode(reservation_code);
        if (reservationDTO != null) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 내역 조회 성공", reservationDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "해당 예약 코드에 대한 정보를 찾을 수 없습니다.", null));
        }
    }

    //예약내역 추가
    @Operation(summary = "예약 추가하기")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createReservation(@RequestBody ReservationDTO reservationDTO, @AuthenticationPrincipal UserDTO principal, @RequestParam MeetingTimeDTO meetingTimeDTO) {
        System.out.println("userDTO11111111111111111111111111111111111111111111111111111111111111111111111111111111111= " + principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "예약 생성 성공", reservationService.createReservation(reservationDTO,principal, meetingTimeDTO)));
    }

    //예약내역 수정
    @Operation(summary = "예약 수정하기")
    @PutMapping("/modify")
    public ResponseEntity<ResponseDTO> updateReservation(@RequestBody ReservationDTO reservationDTO) {
        String result = reservationService.updateReservation(reservationDTO);
        if (result.equals("Reservation updated successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "예약 수정 성공", null));
        } else {
            // 예약이 실패한 경우를 처리할 수 있도록 적절한 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "예약 수정 실패", null));
        }
    }
    // 예약내역 삭제
    @Operation(summary = "예약 삭제하기")
    @DeleteMapping("/delete/{reservationCode}")
    public ResponseEntity<ResponseDTO> deleteReservation(@PathVariable int reservationCode) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "예약 삭제 성공", reservationService.deleteReservation(reservationCode)));
    }

    //참석자만 삭제
    @DeleteMapping("/delete/attendee/{reservationCode}")
    public ResponseEntity<ResponseDTO> deleteAttendee(@PathVariable int reservationCode) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "참석자 삭제 성공", reservationService.deleteAttendee(reservationCode)));
    }

    //참석자 예약코드로 조회
    @GetMapping("/attendee/{reservationCode}")
    public ResponseEntity<ResponseDTO> attendeeByReservationCode(@PathVariable int reservationCode) {
        List<AttendeeDTO> attendeeDTOs = reservationService.attendeesByReservationCode(reservationCode);
        if (!attendeeDTOs.isEmpty()) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 코드를 활용한 참석자 조회 성공", attendeeDTOs));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "해당 예약 코드에 대한 정보를 찾을 수 없습니다.", null));
        }
    }


}


