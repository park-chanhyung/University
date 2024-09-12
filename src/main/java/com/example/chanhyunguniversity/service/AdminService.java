package com.example.chanhyunguniversity.service;

import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserEntity adminCreate(String username, String password)
    {
        UserEntity admin = new UserEntity();

        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ADMIN"); // 관리자 역할 설정
        this.userRepository.save(admin);
        return admin;
    }
}



