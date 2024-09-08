package dev.kazimiruk.tasksystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponse {

    private String title;

    private String description;

    @JsonProperty("creation_time")
    private LocalDateTime creationTime;

    private PersonResponse author;

    private PersonResponse performer;
}
