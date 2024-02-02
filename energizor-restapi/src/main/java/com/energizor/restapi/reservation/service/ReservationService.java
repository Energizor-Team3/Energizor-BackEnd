package com.energizor.restapi.reservation.service;

import com.energizor.restapi.reservation.dto.AttendeeDTO;
import com.energizor.restapi.reservation.dto.ReservationDTO;
import com.energizor.restapi.reservation.entity.Attendee;
import com.energizor.restapi.reservation.entity.Reservation;
import com.energizor.restapi.reservation.repository.AttendeeRepository;
import com.energizor.restapi.reservation.repository.ReservationRepository;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
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

    public AttendeeDTO selectAttendee(int att_code){
        Reservation attendee = reservationRepository.findById(att_code).get();
        AttendeeDTO attendeeDTO = modelMapper.map(attendee, AttendeeDTO.class);
        return attendeeDTO;
    }

    public List<AttendeeDTO> selectAllAttendees() {
        List<Attendee> allReservations = attendeeRepository.findAll();
        return allReservations.stream()
                .map(reservation -> modelMapper.map(reservation, AttendeeDTO.class))
                .collect(Collectors.toList());
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

}
