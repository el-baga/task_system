package dev.kazimiruk.tasksystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kazimiruk.tasksystem.config.SecurityConfig;
import dev.kazimiruk.tasksystem.dto.request.TaskRequest;
import dev.kazimiruk.tasksystem.repository.PersonRepository;
import dev.kazimiruk.tasksystem.security.JwtUtils;
import dev.kazimiruk.tasksystem.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@MockBeans({
        @MockBean(TaskController.class),
        @MockBean(TaskService.class),
        @MockBean(JwtUtils.class),
        @MockBean(PersonRepository.class)
})
class TaskControllerTest {

    @Autowired
    private MockMvc mock;

    @Test
    @DisplayName("Создание задачи неавторизованным пользователем")
    @WithAnonymousUser
    void when_UnauthorizedPersonCreateTask_Expect_Unauthorized() throws Exception {
        this.mock.perform(post("/api/task/create"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Создание задачи авторизованным пользователем")
    @WithMockUser("1")
    void when_AuthorizedPersonCreateTask_Expect_Ok() throws Exception {
        String title = "write code using java or C++";
        String description = "be sure to add validation on request params";
        this.mock.perform(post("/api/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getTaskRequest(title, description)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление задачи неавторизованным пользователем")
    @WithAnonymousUser
    void when_UnauthorizedPersonEditTask_Expect_Unauthorized() throws Exception {
        this.mock.perform(put("/api/task/edit/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Обновление задачи авторизованным пользователем")
    @WithMockUser("1")
    void when_AuthorizedPersonUpdateTask_Expect_Ok() throws Exception {
        String title = "write code using java or C++";
        String description = "be sure to add some custom annotations";
        this.mock.perform(put("/api/task/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getTaskRequest(title, description)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление задачи неавторизованным пользователем")
    @WithAnonymousUser
    void when_UnauthorizedPersonDeleteTask_Expect_Unauthorized() throws Exception {
        this.mock.perform(put("/api/task/delete/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Удаление задачи авторизованным пользователем")
    @WithMockUser("1")
    void when_AuthorizedPersonDeleteTask_Expect_Ok() throws Exception {
        this.mock.perform(delete("/api/task/delete/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение задач пользователей неавторизованным пользователем")
    @WithAnonymousUser
    void when_UnauthorizedPersonGetTasks_Expect_Unauthorized() throws Exception {
        this.mock.perform(get("/api/task/get/all"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Получение задач пользователей авторизованным пользователем")
    @WithMockUser("1")
    void when_AuthorizedPersonGetTasks_Expect_Ok() throws Exception {
        this.mock.perform(get("/api/task/get/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Установка исполнителя на задачу неавторизованным пользователем")
    @WithAnonymousUser
    void when_UnauthorizedPersonSetTaskPerformer_Expect_Unauthorized() throws Exception {
        this.mock.perform(post("/api/task/1/performer").param("performer_id", "1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Установка исполнителя на задачу авторизованным пользователем")
    @WithMockUser("1")
    void when_AuthorizedPersonSetTaskPerformer_Expect_Ok() throws Exception {
        this.mock.perform(post("/api/task/1/performer").param("performer_id", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String getTaskRequest(String title, String description) throws JsonProcessingException {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle(title);
        taskRequest.setDescription(description);
        return new ObjectMapper().writeValueAsString(taskRequest);
    }
}
