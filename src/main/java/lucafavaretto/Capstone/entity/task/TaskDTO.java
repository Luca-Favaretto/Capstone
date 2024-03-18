package lucafavaretto.Capstone.entity.task;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDTO(

        @NotEmpty(message = "Title is required!")
        @Size(min = 4, max = 255, message = "Title must be 4 to 255 characters long ")
        String title,

        @NotEmpty(message = "Description is required!")
        @Size(min = 4, max = 255, message = "Description must be 4 to 255 characters long ")
        String description,
        @FutureOrPresent(message = "Expiration Date hour must be in the future or present!")
        LocalDate expirationDate,

        @NotNull(message = "User ID is required!")
        @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}", message = "Invalid UUID format")
        String userId
) {
}
