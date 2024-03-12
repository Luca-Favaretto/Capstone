package lucafavaretto.Capstone.entity.result;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.entity.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResultDAO extends JpaRepository<Result, UUID> {
    @Query("SELECT COUNT(r)>0 FROM Result r WHERE r.user=:user AND r.title=:title")
    boolean existsByUserAndTitle(User user, String title);

    @Query("SELECT r FROM Result r WHERE r.user=:user")
    Page<Result> findByUser(Pageable pageable, User user);
}
