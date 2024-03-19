package lucafavaretto.Capstone.entity.result;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.entity.task.Task;
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
@RequestMapping("/results")
public class ResultCTRL {
    @Autowired
    ResultSRV resultSRV;

    @GetMapping
    public Page<Result> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "10") int pageSize,
                               @RequestParam(defaultValue = "date") String orderBy) {
        return resultSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result saveStartingHour(@AuthenticationPrincipal User user, @RequestBody @Validated ResultDTO resultDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.resultSRV.save(resultDTO, user);
    }


    @GetMapping("/{id}")
    public Result findById(@PathVariable UUID id) {
        return resultSRV.findById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Result findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated ResultDTO resultDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return resultSRV.findByIdAndUpdate(id, resultDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        resultSRV.deleteById(id);
    }


    @GetMapping("/me")
    public Page<Result> findByUser(@RequestParam(defaultValue = "0") int pageNumber,
                                   @RequestParam(defaultValue = "10") int pageSize,
                                   @RequestParam(defaultValue = "date") String orderBy,
                                   @AuthenticationPrincipal User user) {
        return resultSRV.findByUser(pageNumber, pageSize, orderBy, user);
    }
}
