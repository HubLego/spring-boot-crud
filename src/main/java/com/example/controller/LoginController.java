package com.example.controller;  // подкорректируй под свой базовый пакет

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // вернёт шаблон src/main/resources/templates/login.html
    }
}
