package dev.kazimiruk.tasksystem.controller;

import dev.kazimiruk.tasksystem.dto.request.TaskRequest;
import dev.kazimiruk.tasksystem.dto.response.TaskResponse;
import dev.kazimiruk.tasksystem.swagger.EndpointDescription;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.kazimiruk.tasksystem.service.TaskService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @EndpointDescription(summary = "Создание задачи")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        return ResponseEntity.ok(taskResponse);
    }

    @EndpointDescription(summary = "Редактирование задачи по id")
    @PutMapping(value = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> editTask(@PathVariable Long id, @Valid @RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.editTask(id, taskRequest);
        return ResponseEntity.ok(taskResponse);
    }

    @EndpointDescription(summary = "Удаление задачи по id")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Timestamp deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @EndpointDescription(summary = "Получение всех задач пользователя")
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<TaskResponse> listTaskResponse = taskService.getTasks(page, size);
        return ResponseEntity.ok(listTaskResponse);
    }

    @EndpointDescription(summary = "Назначение исполнителя задачи по id")
    @PostMapping(value = "/{taskId}/performer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> setTaskPerformer(
            @PathVariable Long taskId,
            @RequestParam("performer_id") Long performerId) {
        TaskResponse taskResponse = taskService.setTaskPerformer(taskId, performerId);
        return ResponseEntity.ok(taskResponse);
    }
}
