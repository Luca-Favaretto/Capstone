package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PresenceDAO extends JpaRepository<Presence, UUID> {
    @Query("SELECT COUNT(p)>0 FROM Presence p WHERE p.date=:date AND p.user=:user")
    boolean existsByDateAndUser(LocalDate date, User user);
}
