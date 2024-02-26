package com.energizor.restapi.attendance.service;

import com.energizor.restapi.attendance.dto.CommuteDTO;
import com.energizor.restapi.attendance.entity.Commute;
import com.energizor.restapi.attendance.entity.User;
import com.energizor.restapi.attendance.repository.CommuteRepository;
import com.energizor.restapi.attendance.repository.UserAttendanceRepository;
import com.energizor.restapi.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceService {

    private final CommuteRepository commuteRepository;
    private final UserAttendanceRepository userAttendanceRepository;


    private final ModelMapper modelMapper;

    public AttendanceService(CommuteRepository commuteRepository, UserAttendanceRepository userAttendanceRepository) {
        this.commuteRepository = commuteRepository;
        this.userAttendanceRepository = userAttendanceRepository;

        this.modelMapper = new ModelMapper();
    }


    /*----------------------------------------------------------------------------------------------------------------*/
    /* 출퇴근 전직원 목록 조회 */
    public List<CommuteDTO> findAllUsersList() {
        log.info("allUsersList start ==========================");
        List<Commute> commuteList = commuteRepository.findAll();

        ModelMapper modelMapper = new ModelMapper();

        // 충돌을 일으키는 매핑을 명시적으로 해결
        modelMapper.typeMap(Commute.class, CommuteDTO.class)
                .addMappings(mapping -> mapping.map(Commute::getCCode, CommuteDTO::setCCode))
                .addMappings(mapping -> mapping.map(Commute::getCDate, CommuteDTO::setCDate))
                .addMappings(mapping -> mapping.map(Commute::getCStartTime, CommuteDTO::setCStartTime))
                .addMappings(mapping -> mapping.map(Commute::getCEndTime, CommuteDTO::setCEndTime))
                .addMappings(mapping -> mapping.map(Commute::getCState, CommuteDTO::setCState));




        return commuteList.stream()
                .map(commute -> modelMapper.map(commute, CommuteDTO.class))
                .collect(Collectors.toList());


    }

    /* 출퇴근 직원 목록 조회 */
    public List<CommuteDTO> getAttendanceListByUserCode(int userCode) {
        User user = userAttendanceRepository.findById(userCode)
                .orElseThrow(() -> new NotFoundException("User not found"));
        List<Commute> commuteList = commuteRepository.findByUser(user);

        ModelMapper modelMapper = new ModelMapper();

        // 충돌을 일으키는 매핑을 명시적으로 해결
        modelMapper.typeMap(Commute.class, CommuteDTO.class)
                .addMappings(mapping -> mapping.map(Commute::getCCode, CommuteDTO::setCCode))
                .addMappings(mapping -> mapping.map(Commute::getCDate, CommuteDTO::setCDate))
                .addMappings(mapping -> mapping.map(Commute::getCStartTime, CommuteDTO::setCStartTime))
                .addMappings(mapping -> mapping.map(Commute::getCEndTime, CommuteDTO::setCEndTime))
                .addMappings(mapping -> mapping.map(Commute::getCState, CommuteDTO::setCState));


        return commuteList.stream()
                .map(commute -> modelMapper.map(commute, CommuteDTO.class))
                .collect(Collectors.toList());
    }

    /* 출근 등록 */
    @Transactional
    public CommuteDTO startRegister(int userCode, CommuteDTO commuteDTO) {
        // 사용자 조회
        User user = userAttendanceRepository.findById(userCode)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // CommuteDTO를 Commute 엔티티로 매핑
        Commute commute = modelMapper.map(commuteDTO, Commute.class);
        commute.setUser(user);

        commute.setCDate(commuteDTO.getCDate());
        commute.setCStartTime(commuteDTO.getCStartTime());
        // commute.setCEndTime(commuteDTO.getCEndTime());
        commute.setCState(commuteDTO.getCState());
        commute.setUser(user);

        // Commute 엔티티 저장
        Commute savedCommute = commuteRepository.save(commute);
        return modelMapper.map(savedCommute, CommuteDTO.class);
    }

    /* 퇴근 등록 */
    public CommuteDTO endRegister(int cCode, CommuteDTO commuteDTO) {
        Commute commute = commuteRepository.findById(cCode)
                .orElseThrow(() -> new NotFoundException("commute not found"));

        Commute endRegister = modelMapper.map(commuteDTO, Commute.class);
        endRegister.setCCode(cCode);

        ModelMapper modelMapper = new ModelMapper();

        // 충돌을 일으키는 매핑을 명시적으로 해결
        modelMapper.typeMap(Commute.class, CommuteDTO.class)
                .addMappings(mapping -> mapping.map(Commute::getCCode, CommuteDTO::setCCode))
                .addMappings(mapping -> mapping.map(Commute::getCDate, CommuteDTO::setCDate))
                // .addMappings(mapping -> mapping.map(Commute::getCStartTime, CommuteDTO::setCStartTime))
                .addMappings(mapping -> mapping.map(Commute::getCEndTime, CommuteDTO::setCEndTime))
                .addMappings(mapping -> mapping.map(Commute::getCState, CommuteDTO::setCState));

        return modelMapper.map(commuteRepository.save(endRegister), CommuteDTO.class);
    }


}