package com.example.controller; // ваш пакет

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")           // на корень сайта
    public String home() {
        return "home";         // возвращает шаблон home.html
    }
}
