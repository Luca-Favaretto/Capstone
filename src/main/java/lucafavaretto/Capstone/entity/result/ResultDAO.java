package lucafavaretto.Capstone.entity.result;

import lucafavaretto.Capstone.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResultDAO extends JpaRepository<Result, UUID> {
    @Query("SELECT COUNT(r)>0 FROM Result r WHERE r.user=:user AND r.title=:title")
    boolean existsByUserAndTitle(User user, String title);
}
