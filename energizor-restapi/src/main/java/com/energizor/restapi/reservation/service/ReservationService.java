package com.energizor.restapi.reservation.service;

import com.energizor.restapi.reservation.dto.AttendeeDTO;
import com.energizor.restapi.reservation.dto.ReservationDTO;
import com.energizor.restapi.reservation.entity.Attendee;
import com.energizor.restapi.reservation.entity.Reservation;
import com.energizor.restapi.reservation.repository.AttendeeRepository;
import com.energizor.restapi.reservation.repository.ReservationRepository;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AttendeeRepository attendeeRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;



    public ReservationService(ReservationRepository reservationRepository, AttendeeRepository attendeeRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.attendeeRepository = attendeeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    //예약내역 전체조회
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

    //예약내역 추가
    @Transactional
    public String createReservation(ReservationDTO reservationDTO, UserDTO userDTO) {
        System.out.println("userDTO service11111111111111111111111111111111 = " + userDTO);
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();
        // 예약 객체의 날짜 설정
        reservationDTO.setReservationDate(currentDate);
        reservationDTO.setUserCode(userDTO);
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        Reservation result = reservationRepository.save(reservation);

        System.out.println("result22222222222222222222222 = " + result);

        int[] attendeeUser = reservationDTO.getMember();
        for (int i = 0; i < attendeeUser.length; i++){

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa" + attendeeUser[i]);

            User user123 = userRepository.findByUserCode(attendeeUser[i]);

            System.out.println("user123 = " + user123);
            Attendee attendee = new Attendee();
            attendee.reservation(result);
            attendee.userCode(user123);

            attendeeRepository.save(attendee);
        }
        return "등록성공";
    }

    //예약내역 수정
    @Transactional
    public String updateReservation(ReservationDTO reservationDTO) {
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();
        // 예약 객체의 날짜 설정
        reservationDTO.setReservationDate(currentDate);

        log.info("[ReservationService] updateReservation Start ===================================");
        log.info("[ReservationService] ReservationDTO : " + reservationDTO);

        Reservation reservation = reservationRepository.findById(reservationDTO.getReservationCode()).get();

            attendeeRepository.deleteAllByReservationReservationCode(reservationDTO.getReservationCode());

        int[] attendeeUser = reservationDTO.getMember();
        for (int i = 0; i < attendeeUser.length; i++){

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa" + attendeeUser[i]);

            User user123 = userRepository.findByUserCode(attendeeUser[i]);

            System.out.println("user123 = " + user123);
            Attendee attendee = new Attendee();
            attendee.reservation(reservation);
            attendee.userCode(user123);

            attendeeRepository.save(attendee);
        }
        /* update를 위한 엔티티 값 수정 */
        reservation = reservation.reservationCode(reservationDTO.getReservationCode())
                .reservationDate(reservationDTO.getReservationDate())
                .reservationContent(reservationDTO.getReservationContent())
                .build();

        return "Reservation updated successfully";

    }
    //예약내역 삭제
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
    public List<AttendeeDTO> attendeesByReservationCode(int reservationCode) {
        List<Attendee> attendees = attendeeRepository.findByReservationReservationCode(reservationCode);
        return attendees.stream()
                .map(attendee -> modelMapper.map(attendee, AttendeeDTO.class))
                .collect(Collectors.toList());
    }

    //참석자 추가
//    @Transactional
//    public Object createAttendee(AttendeeDTO attendeeDTO) {
//        // AttendeeDTO에서 UserDTO 배열을 가져옵니다.
//        UserDTO[] userDTOs = attendeeDTO.getUserCode();
//
//        // UserDTO 배열을 Attendee 객체로 매핑하여 저장합니다.
//        for (UserDTO userDTO : userDTOs) {
//            // Attendee 객체 생성 및 매핑
//            Attendee attendee = new Attendee();
//            attendee.setAttCode(attendeeDTO.getAttCode());
//            attendee.setReservationCode(attendeeDTO.getReservationCode());
//            attendee.setUserCode(userDTO);
//
//            // Attendee 저장
//            attendeeRepository.save(attendee);
//        }
//
//        return "참석자 추가 성공";
//    }

    //참석자 수정







}



