package lucafavaretto.Capstone.entity.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContractDAO extends JpaRepository<Contract, UUID> {
}
