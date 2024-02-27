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
        // 사용자 정보 매핑
        User user = modelMapper.map(userDTO, User.class);

        // 예약 객체 생성 및 속성 설정
        Reservation reservation = new Reservation();
        reservation.reservationDate(LocalDate.now());
        reservation.userCode(user);
        // MeetCode를 문자열에서 정수형으로 변환하여 Meet 객체 조회하여 설정
//        int meetCode = Integer.parseInt(String.valueOf(reservationDTO.getMeetCode()));
        Meet meet = meetRepository.findByMeetCode(reservationDTO.getMeet().getMeetCode());
        System.out.println("reservationDTO.getMeetCode().getMeetCode() = " + reservationDTO.getMeet().getMeetCode());
        System.out.println("meet = " + meet);
        reservation.meetCode(meet);
        reservation.reservationContent(reservationDTO.getReservationContent());

        // 예약 저장
        Reservation savedReservation = reservationRepository.save(reservation);


        // 참석자 추가
        if (reservationDTO.getMember() != null && reservationDTO.getMember().size() > 0) {
            for (com.energizor.restapi.reservation.dto.UserDTO userId : reservationDTO.getMember()) {
                User attendeeUser = userRepository.findByUserCode(userId.getUserCode());
                if (attendeeUser != null) {
                    Attendee attendee = new Attendee();
                    attendee.setReservation(savedReservation);
                    attendee.setUserCode(attendeeUser);
                    attendeeRepository.save(attendee);
                } else {
                    // 사용자가 존재하지 않는 경우에 대한 처리
                    System.out.println("참석자를 찾을 수 없습니다: " + userId);
                }
            }
        }

        // 예약에 대한 시간대 추가
        MeetingTime startTime = meetingTimeRepository.findByTime(reservationDTO.getStartTime());
        MeetingTime endTime = meetingTimeRepository.findByTime(reservationDTO.getEndTime());

        if (startTime != null && endTime != null) {
            int startMeetTime = startTime.getMeetTime();
            int endMeetTime = endTime.getMeetTime();
            for (int meetTime = startMeetTime; meetTime <= endMeetTime; meetTime++) {
                ReservationTime reservationTime = new ReservationTime();
                reservationTime.setReservationCode(savedReservation.getReservationCode());
                reservationTime.setMeetTime(meetTime);
                reservationTimeRepository.save(reservationTime);
            }
        } else {
            // 시작 시간 또는 종료 시간을 찾을 수 없는 경우에 대한 처리
            System.out.println("시작 시간 또는 종료 시간을 찾을 수 없습니다.");
        }

        return "등록 성공";
    }

    //예약내역 수정
//    @Transactional
//    public String updateReservation(ReservationDTO reservationDTO) {
//        // 현재 날짜 가져오기
//        LocalDate currentDate = LocalDate.now();
//        // 예약 객체의 날짜 설정
//        reservationDTO.setReservationDate(currentDate);
//
//        log.info("[ReservationService] updateReservation Start ===================================");
//        log.info("[ReservationService] ReservationDTO : " + reservationDTO);
//
//        Reservation reservation = reservationRepository.findById(reservationDTO.getReservationCode()).get();
//
//            attendeeRepository.deleteAllByReservationReservationCode(reservationDTO.getReservationCode());
//
//
//        String[] attendeeUser = reservationDTO.getMember();
//        for (int i = 0; i < attendeeUser.length; i++){
//
//            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa" + attendeeUser[i]);
//
//            User user123 = userRepository.findByUserCode(Integer.parseInt(attendeeUser[Integer.valueOf(i)]));
//
//            System.out.println("user123 = " + user123);
//            Attendee attendee = new Attendee();
//            attendee.reservation(reservation);
//            attendee.userCode(user123);
//
//            attendeeRepository.save(attendee);
//        }
//        /* update를 위한 엔티티 값 수정 */
//        reservation = reservation.reservationCode(reservationDTO.getReservationCode())
//                .reservationDate(reservationDTO.getReservationDate())
//                .reservationContent(reservationDTO.getReservationContent())
//                .build();
//
//        return "Reservation updated successfully";
//
//    }
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



