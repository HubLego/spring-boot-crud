package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {

    private final UserService userService;
    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails auth) {
        User user = userService.findByEmail(auth.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
