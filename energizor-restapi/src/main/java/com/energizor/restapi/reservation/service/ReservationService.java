package com.energizor.restapi.reservation.service;

import com.energizor.restapi.reservation.dto.AttendeeDTO;
import com.energizor.restapi.reservation.dto.ReservationDTO;
import com.energizor.restapi.reservation.entity.Attendee;
import com.energizor.restapi.reservation.entity.Reservation;
import com.energizor.restapi.reservation.repository.AttendeeRepository;
import com.energizor.restapi.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AttendeeRepository attendeeRepository;

    private final ModelMapper modelMapper;



    public ReservationService(ReservationRepository reservationRepository, AttendeeRepository attendeeRepository, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.attendeeRepository = attendeeRepository;
        this.modelMapper = modelMapper;
    }

    //전체 예약내역 조회
    public List<ReservationDTO> selectAllReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        return allReservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    //예약내역 상세조회
    public ReservationDTO selectReservationByCode(int reservationCode) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationCode);
        return optionalReservation.map(reservation -> modelMapper.map(reservation, ReservationDTO.class)).orElse(null);
    }

    //예약 추가
    @Transactional
    public String createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservationRepository.save(reservation);
        return "등록성공";
    }

    //예약 수정
    @Transactional
    public String updateReservation(ReservationDTO reservationDTO) {

        log.info("[ReservationService] updateReservation Start ===================================");
        log.info("[ReservationService] ReservationDTO : " + reservationDTO);

        Reservation reservation = reservationRepository.findById(reservationDTO.getReservationCode()).get();
        /* update를 위한 엔티티 값 수정 */
        reservation = reservation.reservationCode(reservationDTO.getReservationCode())
                .reservationDate(reservationDTO.getReservationDate())
                .reservationContent(reservationDTO.getReservationContent()).build();

        return "Reservation updated successfully";
    }
    //예약내역삭제
    @Transactional
    public String deleteReservation(int reservationCode) {
        log.info("[ReservationService] deleteReservation Start ===================================");
        log.info("[ReservationService] Reservation Code : " + reservationCode);

        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationCode);
        if (optionalReservation.isPresent()) {
            reservationRepository.delete(optionalReservation.get());
            return "Reservation delete successfully";
        } else {
            return "Reservation not found";
        }
    }

    //참석자 예약코드로 조회
    public AttendeeDTO attendeeByReservationCode(int reservationCode) {
        Optional<Attendee> optionalAttendee = attendeeRepository.findById(reservationCode);
        return optionalAttendee.map(attendee -> modelMapper.map(attendee, AttendeeDTO.class)).orElse(null);
    }





}



