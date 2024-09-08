package dev.kazimiruk.tasksystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    @Schema(example = "Title task")
    @NotBlank(message = "Заголовок не должен быть пустым")
    private String title;

    @Schema(example = "Task description")
    @NotBlank(message = "Описание задачи не может быть пустым")
    private String description;

}
