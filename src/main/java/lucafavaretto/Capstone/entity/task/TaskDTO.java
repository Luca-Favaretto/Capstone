package lucafavaretto.Capstone.entity.task;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDTO(
        String title,
        String description,
        LocalDate expirationDate,
        UUID userId
) {
}
