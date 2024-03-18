package lucafavaretto.Capstone.auth.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ValueDTO(
        @NotNull(message = "Value is required!")
        @Min(value = 1, message = "Value must be at least 1")
        @Max(value = 10, message = "Value cannot exceed 10")
        int value
) {
}
