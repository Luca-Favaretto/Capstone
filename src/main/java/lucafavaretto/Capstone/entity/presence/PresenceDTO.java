package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.auth.user.User;

import java.time.LocalDate;

public record PresenceDTO(
        LocalDate date
) {
}
