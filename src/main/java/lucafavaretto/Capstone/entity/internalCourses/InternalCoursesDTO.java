package lucafavaretto.Capstone.entity.internalCourses;

import jakarta.validation.constraints.*;

public record InternalCoursesDTO(
        @NotEmpty(message = "Title is required!")
        @Size(min = 4, max = 255, message = "Title must be 4 to 255 characters long ")
        String title,
        @NotNull(message = "Hours is required!")
        @Min(value = 10, message = "Hours must be at least 10")
        @Max(value = 200, message = "Hours cannot exceed 200")
        int hours,
        @NotEmpty(message = "Description is required!")
        @Size(min = 4, max = 255, message = "Description must be 4 to 255 characters long ")
        String description
) {
}
