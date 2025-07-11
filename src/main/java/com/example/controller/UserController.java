package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", service.getAll());
        return "user-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Добавляем ошибки в модель для отображения в JSP
            if (result.hasFieldErrors("name")) {
                model.addAttribute("nameError", result.getFieldError("name").getDefaultMessage());
            }
            if (result.hasFieldErrors("email")) {
                model.addAttribute("emailError", result.getFieldError("email").getDefaultMessage());
            }

            // Добавляем объект user обратно в модель
            model.addAttribute("user", user);
            return "user-form";
        }
        service.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", service.getById(id));
        return "user-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/users";
    }
}
