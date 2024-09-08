package dev.kazimiruk.tasksystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System API",
                version = "1.0",
                description = "API for manage tasks properly",
                contact = @Contact(name = "Богдан Казимирук", email = "bogdan.kazimiruk@mail.ru")
        ),
        servers = {
                @Server(
                        description = "localhost",
                        url = "http://localhost:8080/"
                )
        }
)
@SecurityScheme(
        name = "JWT Token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
