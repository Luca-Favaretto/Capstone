package lucafavaretto.Capstone.entity.presence;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.enums.AbstinenceStatus;
import lucafavaretto.Capstone.enums.ContractTypology;

import java.time.LocalDate;

public record PresenceAbstinenceDTO(
        @NotBlank(message = "Date  is required!")
        LocalDate date,
        @NotEmpty(message = "Abstinence status  is required!")
        @Size(min = 6, max = 7, message = "Abstinence status typology must be  6 to 7 characters long ")
        String abstinenceStatus
) {
    public AbstinenceStatus getAbstinenceStatus() {
        try {
            return AbstinenceStatus.valueOf(abstinenceStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Contract Typology value: " + abstinenceStatus + ". Correct value: PRESENT, PERMIT, HOLIDAY");
        }
    }
}
