package lucafavaretto.Capstone.entity.presence;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.entity.result.Result;
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
@RequestMapping("/presences")
public class PresenceCTRL {

    @Autowired
    PresenceSRV presenceSRV;

    @GetMapping
    public Page<Presence> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "date") String orderBy) {
        return presenceSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Presence saveStartingHour(@AuthenticationPrincipal User user) {
        return this.presenceSRV.saveStartingHour(user);
    }

    @PostMapping("/finish/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Presence saveFinishHour(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        return this.presenceSRV.saveFinishHour(user, id);
    }

    @PostMapping("/abstinence")
    @ResponseStatus(HttpStatus.CREATED)
    public Presence saveFinishHour(@AuthenticationPrincipal User user, @RequestBody @Validated PresenceAbstinenceDTO presenceAbstinenceDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.presenceSRV.saveAbstinence(presenceAbstinenceDTO, user);
    }

    @GetMapping("/{id}")
    public Presence findById(@PathVariable UUID id) {
        return presenceSRV.findById(id);
    }


    @PutMapping("me/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Presence findByIdAndUpdate(@PathVariable UUID id, @RequestBody PresenceFullDTO presenceFullDTO) {
        return presenceSRV.findByIdAndUpdate(id, presenceFullDTO);
    }

    @DeleteMapping("me/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        presenceSRV.deleteById(id);
    }

    @GetMapping("/me")
    public Page<Presence> findByUser(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "10") int pageSize,
                                     @RequestParam(defaultValue = "date") String orderBy,
                                     @AuthenticationPrincipal User user) {
        return presenceSRV.findByUser(pageNumber, pageSize, orderBy, user);
    }

}
