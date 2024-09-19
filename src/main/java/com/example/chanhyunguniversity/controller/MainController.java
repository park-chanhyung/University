package com.example.chanhyunguniversity.controller;

import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.service.SubjectService;
import com.example.chanhyunguniversity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
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
    public String mainPage(Model model, Principal principal) {

        String username = principal.getName();
        Optional<UserEntity> userOptional = userService.getUserByUsername(username);

        if(userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            List<SubjectEntity> subjects = subjectService.getAllSubjects();
            List<SubjectEntity> registeredSubjects = subjectService.getRegisteredSubjects(username);

            model.addAttribute("ableCredits", user.getAbleCredits());
            model.addAttribute("totalCredits", user.getTotalCredits());
            model.addAttribute("subjects", subjects);
            model.addAttribute("registeredSubjects", registeredSubjects);
        }
        return "main";  // 이 HTML 파일의 이름을 "main.html"로 저장해야 합니다.
    }

}
