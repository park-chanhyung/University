package com.example.chanhyunguniversity.repository;

import com.example.chanhyunguniversity.domain.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity,Long> {
    Page<SubjectEntity> findAll(Specification<SubjectEntity> spec, Pageable pageable);

    Optional<SubjectEntity> findByClassNumber(String classNumber);
}
