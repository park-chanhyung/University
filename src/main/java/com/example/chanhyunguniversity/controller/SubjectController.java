package com.example.chanhyunguniversity.controller;

import com.example.chanhyunguniversity.config.DataNotFoundException;
import com.example.chanhyunguniversity.domain.ProfessorEntity;
import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.form.SubjectForm;
import com.example.chanhyunguniversity.repository.ProfessorRepository;
import com.example.chanhyunguniversity.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {


    private final SubjectService subjectService;
    private final ProfessorRepository professorRepository;
    @ModelAttribute("professors")
    public List<ProfessorEntity> getProfessors() {
        List<ProfessorEntity> professors = professorRepository.findAll();
        System.out.println("교수 목록 크기: " + professors.size());  // 디버깅용
        professors.forEach(p -> System.out.println("교수 이름: " + p.getProfessorName()));  // 디버깅용
        return professors;
    }
    @GetMapping("/create")
    public String create(SubjectForm subjectForm) {
        return "subject_create";
    }

    //수강생성
    @PostMapping("/create")
    public String createSubject(@Valid SubjectForm subjectForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "subject_create";
        }
        subjectService.subjectCreate(
                subjectForm.getSubjectName(),
                subjectForm.getClassOverview(),
                subjectForm.getClassLocation(),
                subjectForm.getClassTime(),
                subjectForm.getClassNumber(),
                subjectForm.getCredits(),
                subjectForm.getClassification(),
                subjectForm.getTotalCapacity(),
                subjectForm.getSubjectGrade(),
                subjectForm.getProfessorName(),
                subjectForm.getDepartment()
        );
        return "redirect:/main";
    }

    //    @GetMapping("/modify/{id}")
//    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
//        Question question = this.questionService.getQuestion(id);
//        if(!question.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//        questionForm.setSubject(question.getSubject());
//        questionForm.setContent(question.getContent());
//        return "question_form";
//    }
    @GetMapping("/update/{id}")
    //수강변경
    public String updateSubject(SubjectForm subjectForm, @PathVariable("id") Long id,Model model) {

        SubjectEntity subjectEntity = this.subjectService.getSubject(id);

        // 기존 데이터를 폼에 설정
        subjectForm.setSubjectName(subjectEntity.getSubjectName());
        subjectForm.setClassOverview(subjectEntity.getClassOverview());
        subjectForm.setClassLocation(subjectEntity.getClassLocation());
        subjectForm.setClassTime(subjectEntity.getClassTime());
        subjectForm.setClassNumber(subjectEntity.getClassNumber());
        subjectForm.setCredits(subjectEntity.getCredits());
        subjectForm.setClassification(subjectEntity.getClassification());
        subjectForm.setTotalCapacity(subjectEntity.getTotalCapacity());
        subjectForm.setSubjectGrade(subjectEntity.getSubjectGrade());
        if (subjectEntity.getProfessor() != null) {
            subjectForm.setProfessorName(subjectEntity.getProfessor().getProfessorName());
        }
        subjectForm.setDepartment(subjectEntity.getDepartment());

        model.addAttribute("subjectForm", subjectForm);
        model.addAttribute("subjectId", id);
        return "subject_create";

    }

    @PostMapping("/update/{id}")
    public String updateSubject(@Valid SubjectForm subjectForm, BindingResult bindingResult, @PathVariable("id") Long id,Model model) {

        if (bindingResult.hasErrors()) {
            return "subject_create";
        }

        try {
            SubjectEntity subjectEntity = this.subjectService.getSubject(id);
            this.subjectService.updateSubject(subjectEntity,
                    subjectForm.getSubjectName(),
                    subjectForm.getClassOverview(),
                    subjectForm.getClassLocation(),
                    subjectForm.getClassTime(),
                    subjectForm.getClassNumber(),
                    subjectForm.getCredits(),
                    subjectForm.getClassification(),
                    subjectForm.getTotalCapacity(),
                    subjectForm.getSubjectGrade(),
                    subjectForm.getProfessorName(),
                    subjectForm.getDepartment());
            return "redirect:/admin";
        } catch (DataNotFoundException e) {
            bindingResult.rejectValue("professorName", "invalid.professorName", e.getMessage());
            model.addAttribute("professorList", subjectService.getAllProfessors()); // 교수 목록 추가
            return "subject_create";
        }
    }
    //수강삭제
    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable("id") Long id){
        SubjectEntity subjectEntity = this.subjectService.getSubject(id);
        this.subjectService.deleteSubject(subjectEntity);
        return "redirect:/admin";
    }



    //수강신청
    @PostMapping("/register")
    public String registerSubject(@RequestParam("subjectId") Long subjectId, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            subjectService.registerSubject(subjectId, principal.getName());
            redirectAttributes.addFlashAttribute("message", "수강 신청이 완료되었습니다.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/main";
    }

    //수강취소
    @PostMapping("/cancel")
    public String cancelSubjectRegistration(@RequestParam("subjectId") Long subjectId, Principal principal) {
        String username = principal.getName();
        subjectService.cancelSubjectRegistration(subjectId, username);
        return "redirect:/main"; 
    }
    @GetMapping("/subjectInfo")
    public String subjectInfo(@RequestParam("subjectId")Long subjectId){
        return "redirect:/main";
    }


}

