package dev.kazimiruk.tasksystem.controller;

import dev.kazimiruk.tasksystem.dto.response.PersonResponse;
import dev.kazimiruk.tasksystem.service.PersonService;
import dev.kazimiruk.tasksystem.swagger.EndpointDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class PersonController {

    private final PersonService personService;

    @EndpointDescription(summary = "Получение всех пользователей")
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonResponse>> getAllPersons() {
        List<PersonResponse> listPersonResponse = personService.getAllPersons();
        return ResponseEntity.ok(listPersonResponse);
    }
}
