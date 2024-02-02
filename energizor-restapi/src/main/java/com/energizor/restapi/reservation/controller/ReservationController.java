package com.energizor.restapi.reservation.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.reservation.dto.ReservationDTO;
import com.energizor.restapi.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    //참석자 코드로 조회(X)
    @GetMapping("/attendee/{att_code}")
    public ResponseEntity<ResponseDTO> selectAttendeeDetail(@PathVariable int att_code){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "참석자 조회 성공",
                reservationService.selectAttendee(att_code)));
    }
    //참석자 전체조회 (X)
    @GetMapping("/attendees")
    public ResponseEntity<ResponseDTO> selectAllAttendees(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 참석자 조회 성공",
                reservationService.selectAllAttendees()));
    }

    //예약내역 전체조회
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> selectAllReservations(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 예약 내역 조회 성공",
                reservationService.selectAllReservations()));
    }

    //예약내역 상세조회
    @GetMapping("/{reservationCode}")
    public ResponseEntity<ResponseDTO> selectReservationByCode(@PathVariable int reservationCode){
        ReservationDTO reservationDTO = reservationService.selectReservationByCode(reservationCode);
        if (reservationDTO != null) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 내역 조회 성공", reservationDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, "해당 예약 코드에 대한 정보를 찾을 수 없습니다.", null));
        }
    }

    //예약내역 추가
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.OK, "예약 생성 성공", reservationService.createReservation(reservationDTO)));
    }

}
