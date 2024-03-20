package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.entity.task.Task;
import lucafavaretto.Capstone.enums.AbstinenceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PresenceDAO extends JpaRepository<Presence, UUID> {
    @Query("SELECT COUNT(p)>0 FROM Presence p WHERE p.date=:date AND p.user=:user")
    boolean existsByDateAndUser(LocalDate date, User user);

    @Query("SELECT COUNT(p)>0 FROM Presence p WHERE p.date=:date AND p.user=:user AND p.finishHour IS NOT NULL")
    boolean existsByDateAndUserAndFinishHour(LocalDate date, User user);

    @Query("SELECT p FROM Presence p WHERE p.user=:user")
    Page<Presence> findByUser(Pageable pageable, User user);

    @Query("SELECT COUNT(p) FROM Presence p WHERE p.abstinenceStatus = :status")
    int countPresence(AbstinenceStatus status);

    @Query("SELECT p FROM Presence p WHERE p.date=:date AND p.user=:user")
    Optional<Presence> findByNowAndUser(LocalDate date, User user);

}
