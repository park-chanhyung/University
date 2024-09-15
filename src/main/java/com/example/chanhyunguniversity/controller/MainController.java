package com.example.chanhyunguniversity.controller;

import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.security.auth.Subject;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final SubjectService subjectService;
    @GetMapping("/")
    public String root() {
        return "login";
    }
    @GetMapping("/login")
    public String Login(){
        return "login";
    }
    @GetMapping("/main")
    public String mainPage(Model model) {
        List<SubjectEntity> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "main";  // 이 HTML 파일의 이름을 "main.html"로 저장해야 합니다.
    }

}
