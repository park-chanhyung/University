package com.example.chanhyunguniversity.controller;


import com.example.chanhyunguniversity.domain.NoticeEntity;
import com.example.chanhyunguniversity.form.NoticeForm;
import com.example.chanhyunguniversity.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
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
    private String getlist(Model model){
        List<NoticeEntity> list = noticeService.getList();
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