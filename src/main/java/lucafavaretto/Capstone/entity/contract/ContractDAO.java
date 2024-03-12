package lucafavaretto.Capstone.entity.contract;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.entity.presence.Presence;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractDAO extends JpaRepository<Contract, UUID> {

    @Query("SELECT COUNT(c)>0 FROM Contract c WHERE c.user=:user")
    boolean existsByUser(User user);

    @Query("SELECT c FROM Contract c WHERE c.user=:user")
    Optional<Contract> findByUser(User user);
}
