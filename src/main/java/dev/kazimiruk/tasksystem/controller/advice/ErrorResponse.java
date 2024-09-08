package dev.kazimiruk.tasksystem.controller.advice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;

@Builder
@Value
public class ErrorResponse {

    String error;

    Timestamp timestamp;

    @JsonProperty("error_description")
    String errorDescription;

}
