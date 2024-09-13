package com.example.chanhyunguniversity.service;

import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectEntity subjectCreate(String subjectName,String classOverview ,String classLocation,String classTime,String classNumber,String credits) {
        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectName(subjectName);
        subject.setClassOverview(classOverview);
        subject.setClassLocation(classLocation);
        subject.setClassTime(classTime);
        subject.setClassNumber(classNumber);
        subject.setCredits(credits);
        this.subjectRepository.save(subject);
        return subject;
    }

    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }
}
