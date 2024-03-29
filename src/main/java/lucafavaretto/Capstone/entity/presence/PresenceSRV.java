package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserSRV;
import lucafavaretto.Capstone.entity.contract.Contract;
import lucafavaretto.Capstone.entity.contract.ContractSRV;
import lucafavaretto.Capstone.entity.internalCourses.InternalCourses;
import lucafavaretto.Capstone.entity.internalCourses.InternalCoursesDTO;
import lucafavaretto.Capstone.entity.result.Result;
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
import java.time.LocalTime;
import java.util.UUID;

@Service
public class PresenceSRV {
    @Autowired
    private PresenceDAO presenceDAO;
    @Autowired
    private UserSRV userSRV;
    @Autowired
    private ContractSRV contractSRV;

    public Page<Presence> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return presenceDAO.findAll(pageable);
    }


    public Presence findById(UUID id) {
        return presenceDAO.findById(UUID.fromString(String.valueOf(id)))
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Presence save(UUID id, PresenceFullDTO presenceFullDTO) {
        User found = userSRV.findById(id);
        return presenceDAO.save(new Presence(presenceFullDTO.date(), presenceFullDTO.startingHour(), presenceFullDTO.finishHour(), presenceFullDTO.getAbstinenceStatus(), found));
    }

    public boolean dateExists(User user) {
        return presenceDAO.existsByDateAndUser(LocalDate.now(), user);
    }

    public Presence saveStartingHour(User user) {
        if (dateExists(user)) {
            throw new BadRequestException("Date already update");
        }
        return presenceDAO.save(new Presence(LocalDate.now(), LocalTime.now(), AbstinenceStatus.PRESENT, user));
    }

    public boolean dateExistsFinish(User user) {
        return presenceDAO.existsByDateAndUserAndFinishHour(LocalDate.now(), user);
    }

    public Presence saveFinishHour(User user, UUID id) {
        if (dateExistsFinish(user)) {
            throw new BadRequestException("Date already finish");
        }
        Presence presence = findById(id);
        if (presence.getUser().getId().equals(user.getId())) {
            presence.setFinishHour(LocalTime.now());
        } else throw new BadRequestException("Presence don't connect with user" + user.getId());
        return presenceDAO.save(presence);
    }

    public Presence saveAbstinence(PresenceAbstinenceDTO presenceAbstinenceDTO, User user) {
        if (presenceDAO.existsByDateAndUser(presenceAbstinenceDTO.date(), user))
            throw new BadRequestException("date already update");
        return presenceDAO.save(new Presence(presenceAbstinenceDTO.date(), presenceAbstinenceDTO.getAbstinenceStatus(), user));
    }


    public Presence findByIdAndUpdate(UUID id, PresenceFullDTO presenceFullDTO) {
        Presence found = findById(id);
        found.setDate(presenceFullDTO.date());
        found.setStartingHour(presenceFullDTO.startingHour());
        found.setFinishHour(presenceFullDTO.finishHour());
        found.setAbstinenceStatus(presenceFullDTO.getAbstinenceStatus());
        return presenceDAO.save(found);
    }

    public void deleteById(UUID id) {
        Presence found = findById(id);
        presenceDAO.delete(found);
    }

    public Page<Presence> findByUser(int pageNumber, int pageSize, String orderBy, User user) {
        Sort sort = Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return presenceDAO.findByUser(pageable, userSRV.findById(user.getId()));
    }

    public int getPresencePerCent(User user) {
        Contract contract = contractSRV.findByUser(user);
        int daysFromHiring = contract.getStartingDate().until(LocalDate.now()).getDays();
        System.out.println(daysFromHiring + "hiring");
        int daysWorked = presenceDAO.countPresence(AbstinenceStatus.PRESENT, user);
        System.out.println(daysWorked + "worked");
        return (int) (100 * daysWorked / daysFromHiring) * 100 / 70;
    }

    public Presence findByNowAndUser(User user) {
        return presenceDAO.findByNowAndUser(LocalDate.now(), user).orElseThrow(() -> new BadRequestException("No Presence today"));
    }
}
