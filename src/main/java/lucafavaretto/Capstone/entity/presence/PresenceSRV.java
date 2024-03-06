package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserSRV;
import lucafavaretto.Capstone.entity.internalCourses.InternalCourses;
import lucafavaretto.Capstone.entity.internalCourses.InternalCoursesDTO;
import lucafavaretto.Capstone.enums.AbstinenceStatus;
import lucafavaretto.Capstone.exceptions.BadRequestException;
import lucafavaretto.Capstone.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PresenceSRV {
    @Autowired
    private PresenceDAO presenceDAO;
    @Autowired
    private UserSRV userSRV;

    public Page<Presence> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return presenceDAO.findAll(pageable);
    }

    public Presence findById(UUID id) {
        return presenceDAO.findById(UUID.fromString(String.valueOf(id)))
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Presence saveStartingHour(PresenceDTO presenceDTO, User user) {
        if (presenceDAO.existsByDateAndUser(presenceDTO.date(), user))
            throw new BadRequestException("date already update");
        return presenceDAO.save(new Presence(presenceDTO.date(), LocalDate.now(), AbstinenceStatus.PRESENT, user));
    }

    public Presence saveFinishHour(UUID id, User user) {
        Presence presence = findById(id);
        if (presence.getUser().equals(user))
            presence.setFinishHour(LocalDate.now());
        return presenceDAO.save(presence);

    }

    public Presence saveAbstinence(PresenceAbstinenceDTO presenceAbstinenceDTO, User user) {
        if (presenceDAO.existsByDateAndUser(presenceAbstinenceDTO.date(), user))
            throw new BadRequestException("date already update");
        return presenceDAO.save(new Presence(presenceAbstinenceDTO.date(), presenceAbstinenceDTO.abstinenceStatus(), user));
    }


    public Presence findByIdAndUpdate(UUID id, PresenceFullDTO presenceFullDTO) {
        Presence found = findById(id);
        found.setDate(presenceFullDTO.date());
        found.setStartingHour(presenceFullDTO.startingHour());
        found.setFinishHour(presenceFullDTO.finishHour());
        found.setAbstinenceStatus(presenceFullDTO.abstinenceStatus());
        return presenceDAO.save(found);
    }

    public void deleteById(UUID id) {
        Presence found = findById(id);
        presenceDAO.delete(found);
    }


}
