package lucafavaretto.Capstone.entity.contract;


import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserSRV;
import lucafavaretto.Capstone.entity.presence.Presence;
import lucafavaretto.Capstone.exceptions.BadRequestException;
import lucafavaretto.Capstone.exceptions.NotFoundException;
import lucafavaretto.Capstone.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ContractSRV {
    @Autowired
    ContractDAO contractDAO;
    @Autowired
    UserSRV userSRV;

    public Page<Contract> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return contractDAO.findAll(pageable);
    }

    public Contract findById(UUID id) {
        return contractDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Contract save(ContractDTO contractDTO, UUID userId) {
        User found = userSRV.findById(userId);
        if (contractDAO.existsByUser(found)) throw new BadRequestException("User can have only one contract");
        Contract contract = new Contract(contractDTO.contractTypology(), contractDTO.weeklyHours(), contractDTO.retribution(), contractDTO.startingDate(), found);
        return contractDAO.save(contract);
    }


    public Contract findByIdAndUpdate(UUID id, ContractDTO contractDTO) {
        Contract found = findById(UUID.fromString(String.valueOf(id)));
        found.setContractTypology(contractDTO.contractTypology());
        found.setWeeklyHours(contractDTO.weeklyHours());
        found.setRetribution(contractDTO.retribution());
        found.setStartingDate(contractDTO.startingDate());
        return contractDAO.save(found);
    }

    public void deleteById(UUID id) {
        Contract found = findById(id);
        contractDAO.delete(found);
    }

    public Contract discharged(UUID id) {
        Contract found = findById(id);
        found.setFinishDate(LocalDate.now());
        return contractDAO.save(found);
    }

    public Contract findByUser(User user) {
        return contractDAO.findByUser(userSRV.findById(user.getId())).orElseThrow(() -> new BadRequestException("User contract is null"));
    }
}
