package com.example.chanhyunguniversity.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String classTime; // 수업 시간

    @Column
    private String credits; // 학점

    @Column(unique = true)
    private String classNumber; // 교과목 번호
}
