package lucafavaretto.Capstone.entity.task;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.entity.presence.Presence;
import lucafavaretto.Capstone.entity.presence.PresenceFullDTO;
import lucafavaretto.Capstone.entity.role.Role;
import lucafavaretto.Capstone.entity.role.RoleDTO;
import lucafavaretto.Capstone.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskCTRL {
    @Autowired
    TaskSRV taskSRV;

    @GetMapping
    public Page<Task> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "expirationDate") String orderBy) {
        return taskSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task save(@RequestBody @Validated TaskDTO roleDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.taskSRV.save(roleDTO);
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable UUID id) {
        return taskSRV.findById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Task findByIdAndUpdate(@PathVariable UUID id, @RequestBody TaskDTO taskDTO) {
        return taskSRV.findByIdAndUpdate(id, taskDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        taskSRV.deleteById(id);
    }

    @GetMapping("/me")
    public Page<Task> findByUser(@RequestParam(defaultValue = "0") int pageNumber,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "expirationDate") String orderBy,
                                 @AuthenticationPrincipal User user) {
        return taskSRV.findByUser(pageNumber, pageSize, orderBy, user);
    }

}
