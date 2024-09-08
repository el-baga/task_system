package dev.kazimiruk.tasksystem.controller;

import dev.kazimiruk.tasksystem.dto.request.LoginRequest;
import dev.kazimiruk.tasksystem.dto.request.RegisterRequest;
import dev.kazimiruk.tasksystem.dto.response.PersonResponse;
import dev.kazimiruk.tasksystem.dto.response.RegisterResponse;
import dev.kazimiruk.tasksystem.service.AuthService;

import dev.kazimiruk.tasksystem.swagger.EndpointDescription;
import dev.kazimiruk.tasksystem.swagger.NoAuthEndpointDescription;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @NoAuthEndpointDescription(summary = "Регистрация пользователя")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @NoAuthEndpointDescription(summary = "Авторизация пользователя по email и password")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        PersonResponse personResponse = authService.login(loginRequest);
        return ResponseEntity.ok(personResponse);
    }

    @EndpointDescription(summary = "Выход пользователя из аккаунта")
    @PostMapping(value = "/logout")
    public Timestamp logout() {
        return authService.logout();
    }
}
