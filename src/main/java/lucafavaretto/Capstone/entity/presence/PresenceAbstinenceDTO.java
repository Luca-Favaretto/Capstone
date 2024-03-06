package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.enums.AbstinenceStatus;

import java.time.LocalDate;

public record PresenceAbstinenceDTO(
        LocalDate date, AbstinenceStatus abstinenceStatus
) {
}
