package com.example.config;

import com.example.service.CustomUserDetailsService;
import com.example.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// 1) Аннотация EnableWebSecurity включает WebSecurity в контексте Spring
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// 2) Импорт HttpMethod для REST-правил
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService,
                             LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF (чтобы GET /logout работал без формы)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        // публичные MVC-страницы (HTML, CSS, JS, картинки)
                        .requestMatchers(
                                "/", "/login", "/error",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()

                        // REST-эндпоинты:
                        //   – профиль: доступно USER и ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/profile")
                        .hasAnyRole("USER","ADMIN")
                        //   – все остальные /api/** только для ADMIN
                        .requestMatchers("/api/**")
                        .hasRole("ADMIN")

                        // MVC-эндпоинты:
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/user/**")
                        .hasAnyRole("ADMIN","USER")

                        // всё остальное — только аутентифицированные
                        .anyRequest().authenticated()
                )

                // форма логина
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .successHandler(loginSuccessHandler)
                )

                // logout по GET-запросу
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // наш DAO-провайдер
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
