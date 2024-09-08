package dev.kazimiruk.tasksystem.dto.response;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Builder
public class RegisterResponse {

    private String email;

    @Builder.Default
    private Timestamp timestamp = Timestamp.from(Instant.now());

}
