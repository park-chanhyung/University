package com.example.chanhyunguniversity.controller;

import com.example.chanhyunguniversity.domain.NoticeEntity;
import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.service.NoticeService;
import com.example.chanhyunguniversity.service.SubjectService;
import com.example.chanhyunguniversity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final SubjectService subjectService;
    private final NoticeService noticeService;
    @GetMapping("/")
    public String root(Model model) {
        Optional<NoticeEntity> latestFixedNotice = noticeService.getLatestFixedNotice();
        latestFixedNotice.ifPresent(notice ->{
            model.addAttribute("fixedNotice", notice);
        });
        return "login";
    }
    @GetMapping("/login")
    public String login() {

        return "login";
    }


    @GetMapping("/main")
    public String mainPage(Model model, Principal principal,
                           @RequestParam(value = "kw", defaultValue = "") String kw,
                           @RequestParam(value = "grade", defaultValue = "") String grade,
                           @RequestParam(value = "department", defaultValue = "") String department,
                           @RequestParam(value = "classification", defaultValue = "") String classification,
                           @RequestParam(value = "page", defaultValue = "0") int page) {
        String username = principal.getName();
        Optional<UserEntity> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            List<SubjectEntity> registeredSubjects = subjectService.getRegisteredSubjects(username);
            List<String> departments = subjectService.getAllDepartments();
            List<String> classifications = subjectService.getAllClassifications();
            Page<SubjectEntity> subjectPage = subjectService.getList(page, kw, grade, department, classification);

            model.addAttribute("ableCredits", user.getAbleCredits());
            model.addAttribute("totalCredits", user.getTotalCredits());
            model.addAttribute("kw", kw);
            model.addAttribute("grade", grade);
            model.addAttribute("department", department);
            model.addAttribute("classification", classification);
            model.addAttribute("departments", departments);
            model.addAttribute("classifications", classifications);
            model.addAttribute("registeredSubjects", registeredSubjects);
            model.addAttribute("paging", subjectPage);

        }
        return "main";
    }
    @GetMapping("/search")
   public String searchByClassNumber(@RequestParam("classNumber") String classNumber, Principal principal, Model model, RedirectAttributes redirectAttributes){

        try{
            SubjectEntity subject = subjectService.searchClassNumber(classNumber);
            model.addAttribute("subjcet",subject);
            String username = principal.getName();
            Optional<UserEntity> userByUsername = userService.getUserByUsername(username);
            if(userByUsername.isPresent()) {
                UserEntity user = userByUsername.get();
                List<SubjectEntity> registeredSubjects = subjectService.getRegisteredSubjects(username);
                List<String> departments = subjectService.getAllDepartments();
                List<String> classifications = subjectService.getAllClassifications();

                model.addAttribute("ableCredits", user.getAbleCredits());
                model.addAttribute("totalCredits", user.getTotalCredits());
                model.addAttribute("departments", departments);
                model.addAttribute("classifications", classifications);
                model.addAttribute("registeredSubjects", registeredSubjects);
            }
            Page<SubjectEntity> subjectPage = new PageImpl<>(List.of(subject));
            model.addAttribute("paging",subjectPage);
            return "main";

        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("message","찾을 수 없는 과목코드입니다.");
            return "redirect:/main";
        }
    }
}
