package dev.kazimiruk.tasksystem.controller.advice;

import dev.kazimiruk.tasksystem.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ControllerAdviser {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String cause = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        log.info(ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Ошибка в чтении введенных данных")
                .timestamp(Timestamp.from(Instant.now()))
                .errorDescription(cause)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadRequestException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.info(ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Ошибка в чтении введенных данных")
                .timestamp(Timestamp.from(Instant.now()))
                .errorDescription(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
