package com.example.chanhyunguniversity.repository;

import com.example.chanhyunguniversity.domain.CourseRegistrationEntity;
import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistrationEntity, Long> {

    List<CourseRegistrationEntity> findByStudent(UserEntity student);


    List<CourseRegistrationEntity> findBySubjectIdAndStudentUsername(Long subjectId, String username);

    boolean existsByStudentAndSubject(UserEntity student, SubjectEntity subject);
}

