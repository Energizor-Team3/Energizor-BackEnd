package com.energizor.restapi.reservation.service;

import com.energizor.restapi.reservation.dto.AttendeeDTO;
import com.energizor.restapi.reservation.dto.ReservationDTO;
import com.energizor.restapi.reservation.entity.*;
import com.energizor.restapi.reservation.repository.*;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AttendeeRepository attendeeRepository;
    private final MeetingTimeRepository meetingTimeRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MeetRepository meetRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;



    public ReservationService(ReservationRepository reservationRepository, AttendeeRepository attendeeRepository, MeetingTimeRepository meetingTimeRepository, ReservationTimeRepository reservationTimeRepository, MeetRepository meetRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.attendeeRepository = attendeeRepository;
        this.meetingTimeRepository = meetingTimeRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.meetRepository = meetRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    //전체 예약 조회
    public List<ReservationDTO> selectTotalReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        return allReservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    //내 예약내역 전체조회
    public List<ReservationDTO> selectAllReservations(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        List<Reservation> allReservations = reservationRepository.findByUserCode(user);
        return allReservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    //예약내역 상세조회
    public ReservationDTO selectReservationByCode(int reservationCode) {

        Optional<Reservation> optionalReservation = reservationRepository.findByReservationCode(reservationCode);
        return optionalReservation.map(reservation -> modelMapper.map(reservation, ReservationDTO.class)).orElse(null);
    }

    //예약내역추가
    @Transactional
    public String createReservation(ReservationDTO reservationDTO, UserDTO userDTO) {
        System.out.println("userDTO service11111111111111111111111111111111 = " + userDTO);
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        User user = modelMapper.map(userDTO, User.class);
        // 예약 객체의 날짜 설정

        MeetingTime meet = meetingTimeRepository.findByTime(reservationDTO.getStartTime());
        MeetingTime meet1 = meetingTimeRepository.findByTime(reservationDTO.getEndTime());
        Meet meet2 = meetRepository.findByMeetCode(reservationDTO.getMeetCode().getMeetCode());
        System.out.println("meet1 = " + meet1);
        System.out.println("meet2 = " + meet2);
        System.out.println("meet = " + meet);
        ArrayList<Integer> reservationForm = new ArrayList<>();
        for (int i = meet.getMeetTime(); i <= meet1.getMeetTime(); i++) {
            reservationForm.add(i);
            System.out.println("i = " + i);
        }
        System.out.println("reservationForm = " + reservationForm);

        Reservation reservation = new Reservation();
        reservation.reservationDate(currentDate);
        reservation.userCode(user);
        reservation.meetCode(meet2);
        reservation.reservationContent(reservationDTO.getReservationContent());
        // 회의 시간 정보를 Reservation 엔티티에 설정

        // 예약 저장
        Reservation result = reservationRepository.save(reservation);


        for (int meetTime : reservationForm) {
            ReservationTime reservationTime = new ReservationTime();
            reservationTime.reservationCode(reservation.getReservationCode());
            reservationTime.meetTime(meetTime);
            reservationTimeRepository.save(reservationTime);

        }

        System.out.println("result22222222222222222222222 = " + result);

        int[] attendeeUser = reservationDTO.getMember();
        for (int i = 0; i < attendeeUser.length; i++){
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
    //참석자만 삭제
    @Transactional
    public String deleteAttendee(int reservationCode) {
        log.info("[ReservationService] deleteAttendee Start ===================================");
        log.info("[ReservationService] Reservation Code : " + reservationCode);
        // 예약 코드에 해당하는 모든 참석자 정보를 가져옴
        List<Attendee> attendees = attendeeRepository.findByReservationReservationCode(reservationCode);

        // 참석자 정보 삭제
        if (!attendees.isEmpty()) {
            attendeeRepository.deleteAllByReservationReservationCode(reservationCode);
            return "Attendees deleted successfully";
        } else {
            return "No attendees found for reservation code: " + reservationCode;
        }
    }

    //예약내역 삭제
    @Transactional
    public String deleteReservation(int reservationCode) {
        log.info("[ReservationService] deleteReservation Start ===================================");
        log.info("[ReservationService] Reservation Code : " + reservationCode);

        // 예약 코드에 해당하는 모든 참석자 정보를 가져옴
        List<Attendee> attendees = attendeeRepository.findByReservationReservationCode(reservationCode);

        // 참석자 정보 삭제
        if (!attendees.isEmpty()) {
            attendeeRepository.deleteAll(attendees);
        }
        reservationRepository.deleteById(reservationCode);

        return "성공";
    }

    //참석자 예약코드로 조회
    public List<AttendeeDTO> attendeesByReservationCode(int reservationCode) {
        List<Attendee> attendees = attendeeRepository.findByReservationReservationCode(reservationCode);
        return attendees.stream()
                .map(attendee -> modelMapper.map(attendee, AttendeeDTO.class))
                .collect(Collectors.toList());
    }


}



