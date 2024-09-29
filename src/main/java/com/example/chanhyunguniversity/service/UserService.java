package com.example.chanhyunguniversity.service;



import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<UserEntity> getUserByUsername(String username) {

    return userRepository.findByusername(username);
    }


}
