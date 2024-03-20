package lucafavaretto.Capstone.entity.contract;

import jakarta.validation.constraints.*;
import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.enums.ContractTypology;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public record ContractDTO(
        @NotEmpty(message = "Contract typology is required!")
        @Size(min = 9, max = 9, message = "Contract typology must be 9 characters long ")
        String contractTypology,
        @NotNull(message = "Weekly hours is required!")
        @Min(value = 10, message = "Weekly hours must be at least 10")
        @Max(value = 40, message = "Weekly hours cannot exceed 40")
        int weeklyHours,
        @NotNull(message = "Retribution is required!")
        double retribution,
        @PastOrPresent(message = "Starting date must be in the past or present")
        LocalDate startingDate
) {
    public ContractTypology getContractTypology() {
        try {
            return ContractTypology.valueOf(contractTypology);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Contract Typology value: " + contractTypology + ". Correct value: PART_TIME, FULL_TIME");
        }
    }
}
