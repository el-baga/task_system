package dev.kazimiruk.tasksystem.controller;

import dev.kazimiruk.tasksystem.config.SecurityConfig;
import dev.kazimiruk.tasksystem.repository.PersonRepository;
import dev.kazimiruk.tasksystem.security.JwtUtils;
import dev.kazimiruk.tasksystem.service.PersonService;
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

@WebMvcTest(PersonController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@MockBeans({
        @MockBean(PersonController.class),
        @MockBean(PersonService.class),
        @MockBean(JwtUtils.class),
        @MockBean(PersonRepository.class)
})
class PersonControllerTest {

    @Autowired
    private MockMvc mock;

    @Test
    @DisplayName("Получение всех пользователей неавторизованным пользователем")
    @WithAnonymousUser
    void when_UnauthorizedPersonGetAllPersons_Expect_Unauthorized() throws Exception {
        this.mock.perform(get("/api/user/get/all"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Получение всех пользователей авторизованным пользователем")
    @WithMockUser("1")
    void when_AuthorizedPersonGetAllPersons_Expect_Ok() throws Exception {
        this.mock.perform(get("/api/user/get/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
