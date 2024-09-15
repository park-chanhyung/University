package com.example.chanhyunguniversity.repository;

import com.example.chanhyunguniversity.domain.ProfessorEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity,Long> {


     ProfessorEntity findByProfessorName(String professorName);
}
