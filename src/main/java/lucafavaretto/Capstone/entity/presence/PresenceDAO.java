package lucafavaretto.Capstone.entity.presence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PresenceDAO extends JpaRepository<Presence, UUID> {
}
