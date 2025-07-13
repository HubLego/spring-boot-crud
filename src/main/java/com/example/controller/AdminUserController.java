package com.example.controller;

import com.example.model.User;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, @RequestParam(value = "roles", required = false) List<Long> rolesIds) {
        Set<Role> roles = new HashSet<>();
        if (rolesIds != null) {
            for (Long roleId : rolesIds) {
                roleRepository.findById(roleId).ifPresent(roles::add);
            }
        }
        user.setRoles(roles);

        // Для новых пользователей шифруем пароль!
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            User existing = userService.findById(user.getId());
            if (!user.getPassword().equals(existing.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "user-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
