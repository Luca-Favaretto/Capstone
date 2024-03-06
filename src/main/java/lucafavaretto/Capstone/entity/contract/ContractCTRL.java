package lucafavaretto.Capstone.entity.contract;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserDTO;
import lucafavaretto.Capstone.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/contracts")
public class ContractCTRL {
    @Autowired
    ContractSRV contractSRV;

    @GetMapping
    public Page<Contract> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "retribution") String orderBy) {
        return contractSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Contract save(@RequestBody @Validated ContractDTO contractDTO, @PathVariable UUID id, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.contractSRV.save(contractDTO, id);
    }

    @GetMapping("/{id}")
    public Contract findById(@PathVariable UUID id) {
        return contractSRV.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Contract findByIdAndUpdate(@PathVariable UUID id, @RequestBody ContractDTO contractDTO) {
        return contractSRV.findByIdAndUpdate(id, contractDTO);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        contractSRV.deleteById(id);
    }
}
