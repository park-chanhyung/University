package com.example.chanhyunguniversity.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="subject")
@Entity
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String subjectName;

    @Column
    private String classOverview; // 수업개요

    @Column
    private String classLocation; // 수업 장소

    @Column
    private String subjectGrade; // 교과목 번호
    @Column
    private String classTime; // 수업 시간

    @Column
    private String credits; // 학점

    @Column
    private String classNumber; // 교과목 번호

    @Column
    private String classification; // 분류 (교양, 전공)

    @Column(nullable = false)
    private int totalCapacity;  // 수강인원

    @Column(nullable = false)
    private int remainCapacity;  // 남은자리

    @Column
    private String department;  // 남은자리
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private ProfessorEntity professor;

    @OneToMany(mappedBy = "subject")
    private List<CourseRegistrationEntity> registrations;
}
