package com.example.chanhyunguniversity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String Login(){
        return "login";
    }
    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }

}
