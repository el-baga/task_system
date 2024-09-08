package dev.kazimiruk.tasksystem.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Schema(example = "example@mail.ru")
    @JsonProperty("email")
    @NotBlank(message = "email не должен быть пустым")
    private String email;

    @Schema(example = "example07")
    @JsonProperty("password")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

}
