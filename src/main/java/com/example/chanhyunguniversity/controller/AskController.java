package com.example.chanhyunguniversity.controller;

import com.example.chanhyunguniversity.domain.AskEntity;
import com.example.chanhyunguniversity.domain.NoticeEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.form.AskForm;
import com.example.chanhyunguniversity.service.AskService;
import com.example.chanhyunguniversity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.math.raw.Mod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ask")
@RequiredArgsConstructor
public class AskController {
    private final UserService userService;
    private final AskService askService;

    @GetMapping
    public String ask(Principal principal, Model model) {
        String username = principal.getName();
        Optional<UserEntity> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            model.addAttribute("name", user.getName());
        }
        return "ask";
    }

    @GetMapping("/create")
    public String askForm(AskForm askForm) {
        return "ask_form";
    }

    @PostMapping("/create")
    public String createAsk(@Valid AskForm askForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "ask_form";
        }
        String username = principal.getName();
        askService.createAsk(askForm.getTitle(), askForm.getContent(), askForm.getPhoneNumber(), username);
        return "redirect:/ask";
    }

    @GetMapping("/adminList")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAskList(Model model) {
        List<AskEntity> list = askService.getList();
        model.addAttribute("list", list);
        return "admin_ask_list";
    }

    @PostMapping("/answer/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String answerAsk(@PathVariable("id") Long id, @RequestParam String answer) {
        askService.answerAsk(id, answer);
        return "redirect:/ask/adminList";
    }

    @GetMapping("/list")
    public String userAskList(Model model, Principal principal) {
        String username = principal.getName();
        List<AskEntity> askList = askService.getAskByUsername(username);
        model.addAttribute("askList",askList);
        return "ask_list";
    }
    @GetMapping("/detail/{id}")
    public String askDetail(@PathVariable("id")Long id , Model model , Principal principal){
        String username = principal.getName();
        AskEntity ask = askService.getAskById(id);
        // 해당 문의가 현재 로그인한 사용자의 것인지 확인
        if (!ask.getUser().getUsername().equals(username)) {
            return "redirect:/ask/list";
        }
        model.addAttribute("ask" , ask);
        return "ask_detail";
    }
}
