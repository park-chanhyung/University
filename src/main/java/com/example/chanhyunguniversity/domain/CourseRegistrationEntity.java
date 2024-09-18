package com.example.chanhyunguniversity.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_registration")
public class CourseRegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;


    @Column(nullable = false)
    private LocalDateTime registrationDate;

    // You might want to add a status field (e.g., PENDING, APPROVED, REJECTED)
    @Column(nullable = false)
    private String status;
}