package lucafavaretto.Capstone.entity.contract;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.enums.ContractTypology;

import java.time.LocalDate;

public record ContractDTO(
        ContractTypology contractTypology,
        int weeklyHours,
        double retribution,
        LocalDate startingDate
) {
}
