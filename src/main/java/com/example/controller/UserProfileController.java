package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/user", "/profile"})
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserProfile(Model model, Authentication authentication) {
        // Получаем email текущего пользователя (он у тебя выступает username)
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            // Если вдруг пользователь не найден (маловероятно), редиректим на логин
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user-profile"; // Здесь Thymeleaf-шаблон user-profile.html
    }
}
