package lucafavaretto.Capstone.entity.internalCourses;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserDTO;
import lucafavaretto.Capstone.auth.user.UserSRV;
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

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/internalCourses")
public class InternalCoursesCTRL {

    @Autowired
    InternalCoursesSRV internalCoursesSRV;

    @GetMapping
    public Page<InternalCourses> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "title") String orderBy) {
        return internalCoursesSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InternalCourses save(@RequestBody @Validated InternalCoursesDTO internalCoursesDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.internalCoursesSRV.save(internalCoursesDTO);
    }

    @GetMapping("/{id}")
    public InternalCourses findById(@PathVariable UUID id) {
        return internalCoursesSRV.findById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public InternalCourses findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated InternalCoursesDTO internalCoursesDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return internalCoursesSRV.findByIdAndUpdate(id, internalCoursesDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        internalCoursesSRV.deleteById(id);
    }

    @GetMapping("/notDo")
    public Page<InternalCourses> findNotCompletedInternalCourses(@RequestParam(defaultValue = "0") int pageNumber,
                                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                                 @RequestParam(defaultValue = "title") String orderBy,
                                                                 @AuthenticationPrincipal User user) {
        return internalCoursesSRV.findNotCompletedInternalCourses(pageNumber, pageSize, orderBy, user);
    }

    @GetMapping("/me")
    public Page<InternalCourses> findCoursesByUser(@RequestParam(defaultValue = "0") int pageNumber,
                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "title") String orderBy,
                                                   @AuthenticationPrincipal User user) {
        return internalCoursesSRV.findCoursesByUser(pageNumber, pageSize, orderBy, user);
    }


}
