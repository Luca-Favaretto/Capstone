package lucafavaretto.Capstone.auth.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserNoPassDTO(
        @NotEmpty(message = "Name is required!")
        @Size(min = 3, max = 20, message = "Name must be 3 to 20 characters long ")
        String name,
        @NotEmpty(message = "Surname is required!")
        @Size(min = 3, max = 20, message = "Surname must be 3 to 20 characters long")
        String surname,
        @NotEmpty(message = "Username is required!")
        @Size(min = 3, max = 20, message = "Username must be 3 to 20 characters long")
        String username,
    

        @Email(message = "Email address you provided is not valid.")
        String email
) {
}
