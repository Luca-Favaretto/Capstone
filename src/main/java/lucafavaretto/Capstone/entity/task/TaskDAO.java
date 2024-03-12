package lucafavaretto.Capstone.entity.task;

import lucafavaretto.Capstone.auth.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskDAO extends JpaRepository<Task, UUID> {
    @Query("SELECT t FROM Task t WHERE t.user=:user")
    Page<Task> findByUser(Pageable pageable, User user);
   
}
