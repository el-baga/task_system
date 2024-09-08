package dev.kazimiruk.tasksystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kazimiruk.tasksystem.config.SecurityConfig;
import dev.kazimiruk.tasksystem.dto.request.LoginRequest;
import dev.kazimiruk.tasksystem.dto.request.RegisterRequest;
import dev.kazimiruk.tasksystem.repository.PersonRepository;
import dev.kazimiruk.tasksystem.security.JwtUtils;
import dev.kazimiruk.tasksystem.service.AuthService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@MockBeans({
        @MockBean(AuthController.class),
        @MockBean(AuthService.class),
        @MockBean(JwtUtils.class),
        @MockBean(PersonRepository.class)
})
class AuthControllerTest {

    @Autowired
    private MockMvc mock;

    @Test
    @DisplayName("Register with status 200")
    @WithAnonymousUser
    void when_PersonEnteredRegistrationDataWithCorrectFormat_Expect_Ok() throws Exception {
        String json = getRegisterRequest("Bogdan", "bogdan.kazimiruk@mail.ru", "javathebest2024");
        this.mock.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Введены регистрационные данные с некорректным форматом почты")
    @WithAnonymousUser
    void when_PersonEnteredRegistrationDataWithIncorrectEmailFormat_Expect_BadRequest() throws Exception {
        String firstName = "Станислав";
        String email = "stas@ru";
        String password = "stas2244";
        this.mock.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRegisterRequest(firstName, email, password)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Введены регистрационные данные с некорректным форматом пароля")
    @WithAnonymousUser
    void when_PersonEnteredRegistrationDataWithIncorrectPasswordFormat_Expect_BadRequest() throws Exception {
        String firstName = "Станислав";
        String email = "stas@mail.ru";
        String password = "stas";
        this.mock.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRegisterRequest(firstName, email, password)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Введены регистрационные данные с пустым именем")
    @WithAnonymousUser
    void when_PersonEnteredRegistrationDataWithEmptyFirstName_Expect_BadRequest() throws Exception {
        String email = "stas@mail.ru";
        String password = "stas";
        this.mock.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRegisterRequest(null, email, password)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Введены регистрационные данные с пустой фамилией")
    @WithAnonymousUser
    void when_PersonEnteredRegistrationDataWithEmptyLastName_Expect_BadRequest() throws Exception {
        String firstName = "Станислав";
        String email = "stas@mail.ru";
        String password = "stas";
        this.mock.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getRegisterRequest(firstName, email, password)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Введены правильно логин данные")
    @WithAnonymousUser
    void when_PersonEnteredCorrectLoginData_Expect_Ok() throws Exception {
        String email = "alex@mail.ru";
        String password = "alexjava";
        this.mock.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getLoginRequest(email, password)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Введены логин данные с пустой почтой")
    @WithAnonymousUser
    void when_PersonEnteredLoginDataWithEmptyEmail_Expect_BadRequest() throws Exception {
        String password = "alexjava";
        this.mock.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getLoginRequest(null, password)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Введены логин данные с пустым паролем")
    @WithAnonymousUser
    void when_PersonEnteredLoginDataWithEmptyPassword_Expect_BadRequest() throws Exception {
        String email = "alex@mail.ru";
        this.mock.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getLoginRequest(email, null)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Выход из аккаунта неавторизованным пользователем")
    @WithAnonymousUser
    void when_UnauthorizedPersonLogout_Expect_Unauthorized() throws Exception {
        this.mock.perform(post("/api/auth/logout"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Выход из аккаунта авторизованным пользователем")
    @WithMockUser("1")
    void when_AuthorizedPersonLogout_Expect_Ok() throws Exception {
        this.mock.perform(post("/api/auth/logout"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String getRegisterRequest(String firstName, String email, String password) throws JsonProcessingException {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName(firstName);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        return new ObjectMapper().writeValueAsString(registerRequest);
    }

    private String getLoginRequest(String email, String password) throws JsonProcessingException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        return new ObjectMapper().writeValueAsString(loginRequest);
    }
}
