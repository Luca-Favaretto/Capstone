package lucafavaretto.Capstone.entity.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResultDAO extends JpaRepository<Result, UUID> {
}
