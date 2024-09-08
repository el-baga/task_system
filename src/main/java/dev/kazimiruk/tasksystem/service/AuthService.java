package dev.kazimiruk.tasksystem.service;

import dev.kazimiruk.tasksystem.dto.request.LoginRequest;
import dev.kazimiruk.tasksystem.dto.request.RegisterRequest;
import dev.kazimiruk.tasksystem.dto.response.PersonResponse;
import dev.kazimiruk.tasksystem.dto.response.RegisterResponse;
import dev.kazimiruk.tasksystem.entity.Person;
import dev.kazimiruk.tasksystem.exception.BadRequestException;
import dev.kazimiruk.tasksystem.mapper.PersonMapper;
import dev.kazimiruk.tasksystem.repository.PersonRepository;
import dev.kazimiruk.tasksystem.security.JwtUtils;
import dev.kazimiruk.tasksystem.util.CommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PersonRepository personRepository;

    private final TaskService taskService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        if (personRepository.existsByEmail(email)) {
            throw new BadRequestException("Пользователь с указанной почтой уже существует");
        }

        savePersonToDatabase(registerRequest);
        log.info("Регистрация пользователя с почтой '{}' прошла успешно", email);
        return RegisterResponse.builder()
                .email(email)
                .build();
    }

    private void savePersonToDatabase(RegisterRequest registerRequest) {
        Person person = PersonMapper.INSTANCE.toEntity(registerRequest);
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        personRepository.saveAndFlush(person);
    }

    @Cacheable(cacheNames = "loginCache", cacheManager = "caffeineCacheManager")
    public PersonResponse login(LoginRequest loginRequest) {
        Person person = taskService.getPersonByEmail(loginRequest.getEmail());
        if (!passwordEncoder.matches(loginRequest.getPassword(), person.getPassword())) {
            throw new BadRequestException("Неправильно введен пароль");
        }

        log.info("Аутентификация пользователя с почтой '{}' прошла успешно", person.getEmail());
        return getPersonResponse(person);
    }

    private PersonResponse getPersonResponse(Person person) {
        PersonResponse personResponse = PersonMapper.INSTANCE.toDto(person);
        personResponse.setToken(jwtUtils.generateToken(person));
        return personResponse;
    }

    public Timestamp logout() {
        Long id = CommonUtil.getCurrentUserId();
        Person person = taskService.getPersonById(id);
        log.info("Пользователь с почтой '{}' вышел из аккаунта", person.getEmail());
        SecurityContextHolder.clearContext();
        return Timestamp.from(Instant.now());
    }
}
