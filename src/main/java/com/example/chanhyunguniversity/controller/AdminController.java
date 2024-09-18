package com.example.chanhyunguniversity.controller;

import com.example.chanhyunguniversity.domain.SubjectEntity;
import com.example.chanhyunguniversity.form.AdminCreateForm;
import com.example.chanhyunguniversity.form.SubjectForm;
import com.example.chanhyunguniversity.service.AdminService;
import com.example.chanhyunguniversity.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final SubjectService subjectService;
    @Value("${admin.password}")
    private String adminPassword;

    @GetMapping
    public String adminPage(Model model){
        List<SubjectEntity> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "admin_page";
    }
    @GetMapping("/adminCheck")
    public String adminPasswordPage() {
        return "admin_check";
    }

    @PostMapping("/check-password")
    public String checkAdminPassword(@RequestParam String adminPassword) {
        if (this.adminPassword.equals(adminPassword)) {
            return "redirect:/admin/signup";  // 회원가입 페이지로 리다이렉트
        }
        return "redirect:/admin?error";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("adminCreateForm", new AdminCreateForm());
        return "admin_signup";
    }

    @PostMapping("/signup")
    public String createAdmin(@Valid AdminCreateForm adminCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "admin_signup";
        }
            try {
                adminService.adminCreate(adminCreateForm.getUsername(),adminCreateForm.getPassword1());
            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
                return "admin_signup";
            } catch (Exception e) {
                e.printStackTrace();
                bindingResult.reject("signupFailed", e.getMessage());
                return "admin_signup";
            }
            return "redirect:/login";
    }
}