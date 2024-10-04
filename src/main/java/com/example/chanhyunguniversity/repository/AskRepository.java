package com.example.chanhyunguniversity.repository;

import com.example.chanhyunguniversity.domain.AskEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AskRepository extends JpaRepository<AskEntity,Long> {
    List<AskEntity> findByUserOrderByCreatedAtDesc(UserEntity user);

}
