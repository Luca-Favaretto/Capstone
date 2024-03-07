package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.enums.AbstinenceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record PresenceFullDTO(

        LocalDate date,
        LocalTime startingHour,
        LocalTime finishHour,

        AbstinenceStatus abstinenceStatus
) {
}
