package com.energizor.restapi.reservation.service;

import com.energizor.restapi.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ModelMapper modelMapper;




    public ReservationService(ReservationRepository reservationRepository, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }
}
