package com.example.chanhyunguniversity.repository;

import com.example.chanhyunguniversity.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
     Optional<UserEntity> findByusername(String username);

}
