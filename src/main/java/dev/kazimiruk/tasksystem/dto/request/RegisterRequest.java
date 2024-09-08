package dev.kazimiruk.tasksystem.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Schema(example = "Ivan")
    @JsonProperty("first_name")
    @NotBlank(message = "Поле 'Имя' не должно быть пустым")
    private String firstName;

    @Schema(example = "example@mail.ru")
    @JsonProperty("email")
    @NotBlank(message = "Поле 'email' не должно быть пустым")
    @Email(message = "Неверный формат email", regexp = "^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$")
    private String email;

    @Schema(example = "example07")
    @JsonProperty("password")
    @NotBlank(message = "Поле 'Пароль' не должно быть пустым")
    @Size(min = 8, max =  20, message = "Пароль должен содержать не менее 8 и не более 20 символов")
    private String password;
}
