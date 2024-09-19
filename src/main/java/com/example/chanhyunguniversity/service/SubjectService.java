package com.example.chanhyunguniversity.service;

import com.example.chanhyunguniversity.domain.CourseRegistrationEntity;
import com.example.chanhyunguniversity.domain.ProfessorEntity;
import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.repository.CourseRegistrationRepository;
import com.example.chanhyunguniversity.repository.ProfessorRepository;
import com.example.chanhyunguniversity.repository.SubjectRepository;
import com.example.chanhyunguniversity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    public void subjectCreate(String subjectName, String classOverview, String classLocation,
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
    }

    public void updateSubject(SubjectEntity s,String subjectName, String classOverview, String classLocation,
                              String classTime, String classNumber, String credits, String classification,
                              Integer totalCapacity, String subjectGrade, String professorName, String department) {

        ProfessorEntity professor = professorRepository.findByProfessorName(professorName);

        s.setSubjectName(subjectName);
        s.setClassOverview(classOverview);
        s.setClassLocation(classLocation);
        s.setClassTime(classTime);
        s.setClassNumber(classNumber);
        s.setCredits(credits);
        s.setClassification(classification);
        s.setTotalCapacity(totalCapacity);
        s.setRemainCapacity(totalCapacity); // 초기에는 남은 자리가 전체 수강 정원과 같습니다.
        s.setSubjectGrade(subjectGrade);
        s.setProfessor(professor);
        s.setDepartment(department);
        this.subjectRepository.save(s);
    }

    public void deleteSubject(SubjectEntity subjectEntity) {
        this.subjectRepository.delete(subjectEntity);
    }

    public void registerSubject(Long subjectId, String username) {
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        UserEntity student = userRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("student not found"));

        boolean alreadyRegistered = courseRegistrationRepository.existsByStudentAndSubject(student,subject);
        if(alreadyRegistered){
            throw new RuntimeException("이미 신청한 과목입니다.");

        }

        if (subject.getRemainCapacity() <= 0) {
            throw new RuntimeException("정원초과된 과목입니다.");
        }
        CourseRegistrationEntity registration = new CourseRegistrationEntity();
        registration.setStudent(student);
        registration.setSubject(subject);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("REGISTERED");
        student.addCredits(Integer.parseInt(subject.getCredits()));
        this.courseRegistrationRepository.save(registration);

        subject.setRemainCapacity(subject.getRemainCapacity() - 1);
        subjectRepository.save(subject);
    }

    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<SubjectEntity> getRegisteredSubjects(String username) {
        UserEntity student = userRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<CourseRegistrationEntity> registrations =
                courseRegistrationRepository.findByStudent(student);

        return registrations.stream()
                .map(CourseRegistrationEntity::getSubject)
                .collect(Collectors.toList());
    }

    public void cancelSubjectRegistration(Long subjectId, String username) {

        UserEntity student = userRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        SubjectEntity subjectCredit = subjectRepository.findById(subjectId).orElseThrow(()-> new RuntimeException("subject not found"));
        List<CourseRegistrationEntity> registrations = courseRegistrationRepository
                .findBySubjectIdAndStudentUsername(subjectId, username);

        if (registrations.isEmpty()) {
            throw new RuntimeException("Registration not found");
        }

        // 모든 중복 등록을 취소
        for (CourseRegistrationEntity registration : registrations) {
            courseRegistrationRepository.delete(registration);
           SubjectEntity subject = registration.getSubject();
           student.removeCredits(Integer.parseInt(subject.getCredits()));
            subject.setRemainCapacity(subject.getRemainCapacity() + 1);
            subjectRepository.save(subject);
        }
    }

    public SubjectEntity getSubject(Long id) {
        Optional<SubjectEntity> subject = this.subjectRepository.findById(id);
        if (subject.isPresent()) {
            return subject.get();
        } else {
            throw new RuntimeException("Subject not found");
        }
    }
}
