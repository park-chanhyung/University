package com.example.chanhyunguniversity.controller;


import com.example.chanhyunguniversity.domain.NoticeEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.form.NoticeForm;
import com.example.chanhyunguniversity.service.NoticeService;
import com.example.chanhyunguniversity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final UserService userService;
    @GetMapping("/create")
    private String createNotice(NoticeForm noticeForm){
        return "/notice_create";
    }
    @PostMapping("/create")
    private String createNotice(@Valid NoticeForm noticeForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/admin";
        }
        noticeService.createNotice(noticeForm.getTitle(),noticeForm.getContent(),noticeForm.isFixNotice());
        return "redirect:/admin";

    }
    @GetMapping("/list")
    private String getlist(Model model, Principal principal){
        List<NoticeEntity> list = noticeService.getList();
        String username = principal.getName();
        Optional<UserEntity> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            model.addAttribute("name", user.getName());
        }
        model.addAttribute("list",list);

        return "/notice_list";

    }
    @GetMapping("/update/{id}")
    public String updateNotice(@PathVariable("id") Long id, Model model) {
        NoticeEntity noticeEntity = this.noticeService.getNotice(id);
        NoticeForm noticeForm = new NoticeForm();
        noticeForm.setTitle(noticeEntity.getTitle());
        noticeForm.setContent(noticeEntity.getContent());
        noticeForm.setFixNotice(noticeEntity.isFixNotice());
        model.addAttribute("noticeForm", noticeForm);
        model.addAttribute("noticeId", id);
        return "notice_create";
    }
    @PostMapping("/update/{id}")
    public String updateNotice(@Valid NoticeForm noticeForm, BindingResult bindingResult,
                               @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("noticeId", id);
            return "notice_create";
        }
        this.noticeService.updateNotice(id, noticeForm.getTitle(), noticeForm.getContent(), noticeForm.isFixNotice());
        return "redirect:/notice/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteNotice(@PathVariable("id") Long id) {
        this.noticeService.deleteNotice(id);
        return "redirect:/notice/list";
    }
}