package com.energizor.restapi.users.service;

import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Object selectMyInfo(String userId) {
        log.info("[UserService]  selectMyInfo   Start =============== ");

        User user = userRepository.findByUserId(userId);
        log.info("[UserService]  {} =============== ", user);
        log.info("[UserService]  selectMyInfo   End =============== ");
        return modelMapper.map(user, UserDTO.class);
    }
}
