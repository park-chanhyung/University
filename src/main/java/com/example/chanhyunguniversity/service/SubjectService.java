package com.example.chanhyunguniversity.service;

import com.example.chanhyunguniversity.domain.CourseRegistrationEntity;
import com.example.chanhyunguniversity.domain.ProfessorEntity;
import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.repository.CourseRegistrationRepository;
import com.example.chanhyunguniversity.repository.ProfessorRepository;
import com.example.chanhyunguniversity.repository.SubjectRepository;
import com.example.chanhyunguniversity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    public SubjectEntity subjectCreate(String subjectName, String classOverview, String classLocation,
                                       String classTime, String classNumber, String credits, String classification,
                                       Integer totalCapacity, String subjectGrade, String professorName, String department) {

        ProfessorEntity professor = professorRepository.findByProfessorName(professorName);
        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectName(subjectName);
        subject.setClassOverview(classOverview);
        subject.setClassLocation(classLocation);
        subject.setClassTime(classTime);
        subject.setClassNumber(classNumber);
        subject.setCredits(credits);
        subject.setClassification(classification);
        subject.setTotalCapacity(totalCapacity);
        subject.setRemainCapacity(totalCapacity); // 초기에는 남은 자리가 전체 수강 정원과 같습니다.
        subject.setSubjectGrade(subjectGrade);
        subject.setProfessor(professor);
        subject.setDepartment(department);
        this.subjectRepository.save(subject);
        return subject;
    }

    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public void registerSubject(Long subjectId, String username) {
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        UserEntity student = userRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("student not found"));

        if (subject.getRemainCapacity() <= 0) {
            throw new RuntimeException("정원초과된 과목입니다.");
        }

        CourseRegistrationEntity registration = new CourseRegistrationEntity();
        registration.setStudent(student);
        registration.setSubject(subject);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("REGISTERED");

        this.courseRegistrationRepository.save(registration);

        subject.setRemainCapacity(subject.getRemainCapacity() - 1);
        subjectRepository.save(subject);
    }
}
