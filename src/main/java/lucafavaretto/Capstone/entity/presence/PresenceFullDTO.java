package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.enums.AbstinenceStatus;

import java.time.LocalDate;

public record PresenceFullDTO(

        LocalDate date,
        LocalDate startingHour,
        LocalDate finishHour,

        AbstinenceStatus abstinenceStatus
) {
}
