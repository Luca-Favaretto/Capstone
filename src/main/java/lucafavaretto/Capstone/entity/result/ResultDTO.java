package lucafavaretto.Capstone.entity.result;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ResultDTO(
        @NotEmpty(message = "Title is required!")
        @Size(min = 4, max = 255, message = "Title must be 4 to 255 characters long ")
        String title,
        @NotEmpty(message = "Description is required!")
        @Size(min = 4, max = 255, message = "Description must be 4 to 255 characters long ")
        String description,
        @PastOrPresent(message = "Date must be in the past or present!")
        LocalDate date
) {
}
