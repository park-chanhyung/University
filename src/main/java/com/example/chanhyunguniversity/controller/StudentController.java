package com.example.chanhyunguniversity.controller;

import com.example.chanhyunguniversity.form.SubjectForm;
import com.example.chanhyunguniversity.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subject")
@RequiredArgsConstructor
public class StudentController {


    private final SubjectService subjectService;
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("subjectForm", new SubjectForm());
        return "subject_create";
    }

    @PostMapping("/create")
    public String createSubject(@Valid SubjectForm subjectForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "subject_create";
        }
        subjectService.subjectCreate(subjectForm.getSubjectName(),subjectForm.getClassOverview(),
                subjectForm.getClassLocation(),subjectForm.getClassTime(),subjectForm.getClassNumber() ,subjectForm.getCredits());
        return "redirect:/main";
    }
}
