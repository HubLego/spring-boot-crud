package com.example;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UserCrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCrudApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        return args -> {
            if (roleRepo.findByName("ROLE_ADMIN") == null)
                roleRepo.save(new Role("ROLE_ADMIN"));
            if (roleRepo.findByName("ROLE_USER") == null)
                roleRepo.save(new Role("ROLE_USER"));

            if (userRepo.findByEmail("admin@example.com") == null) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(encoder.encode("1234")); // пароль 1234
                Set<Role> roles = new HashSet<>();
                roles.add(roleRepo.findByName("ROLE_ADMIN"));
                admin.setRoles(roles);
                userRepo.save(admin);
            }
        };
    }
}
