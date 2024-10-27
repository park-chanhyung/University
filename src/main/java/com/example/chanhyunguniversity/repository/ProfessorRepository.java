package com.example.chanhyunguniversity.repository;

import com.example.chanhyunguniversity.domain.ProfessorEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity,Long> {



     // 여러 결과가 있을 경우 첫 번째 결과 반환
     Optional<ProfessorEntity> findFirstByProfessorName(String professorName);

     // 또는 모든 결과를 리스트로 반환
     List<ProfessorEntity> findByProfessorName(String professorName);
}
