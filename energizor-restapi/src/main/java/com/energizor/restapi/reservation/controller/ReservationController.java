package com.energizor.restapi.reservation.controller;

import com.energizor.restapi.common.ResponseDTO;
import com.energizor.restapi.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/attendee/{att_code}")
    public ResponseEntity<ResponseDTO> selectAttendeeDetail(@PathVariable int att_code){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "참석자 조회 성공",
                reservationService.selectAttendee(att_code)));
    }
}
