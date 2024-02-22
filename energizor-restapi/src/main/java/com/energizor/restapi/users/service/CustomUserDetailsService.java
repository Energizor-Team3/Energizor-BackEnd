package com.energizor.restapi.users.service;

import com.energizor.restapi.exception.LoginFailedException;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import com.energizor.restapi.users.entity.UserRole;
import com.energizor.restapi.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public CustomUserDetailsService(UserRepository userRepository,
                                    ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new LoginFailedException("사용자가 존재하지 않습니다.");
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserRole userRole : user.getUserRole()){
            String authorityName = userRole.getAuthority().getAuthName();
            authorities.add(new SimpleGrantedAuthority(authorityName));
        }

        userDTO.setAuthorities(authorities);

        return userDTO;
    }
}
