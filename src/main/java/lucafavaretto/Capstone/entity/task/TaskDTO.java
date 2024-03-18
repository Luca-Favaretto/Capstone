package lucafavaretto.Capstone.entity.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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
        @NotBlank(message = "User is required!")
        UUID userId
) {
}
