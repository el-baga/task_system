package dev.kazimiruk.tasksystem.swagger;

import dev.kazimiruk.tasksystem.controller.advice.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@Operation(
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden",
                        content = @Content)
        },
        parameters = @Parameter(
                name = "Authorization",
                description = "JWT Token",
                required = true,
                in = ParameterIn.HEADER,
                schema = @Schema(type = "string")
        ),
        security = {
                @SecurityRequirement(
                        name = "JWT Token"
                )
        }
)
public @interface EndpointDescription {
    @AliasFor(annotation = Operation.class, attribute = "summary")
    String summary();
}
