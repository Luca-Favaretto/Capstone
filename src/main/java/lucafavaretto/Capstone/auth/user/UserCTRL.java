package lucafavaretto.Capstone.auth.user;

import lucafavaretto.Capstone.entity.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserCTRL {
    @Autowired
    UserSRV userSRV;


    @GetMapping
    public Page<User> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "name") String orderBy) {
        return userSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable UUID id) {
        return userSRV.findById(id);
    }

    ////////////////////////////////////////////////////////////////me action
    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal User authenticatedCustomer) {
        return authenticatedCustomer;
    }

    @PutMapping("/me/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public User findByIdAndUpdate(@PathVariable UUID id, @RequestBody UserDTO eventDTO, @AuthenticationPrincipal User currentAuthenticatedUser) {
        return userSRV.findByIdAndUpdate(currentAuthenticatedUser.getId(), eventDTO);
    }

    @DeleteMapping("/me/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@AuthenticationPrincipal User currentAuthenticatedUser) {
        userSRV.deleteById(currentAuthenticatedUser.getId());
    }

    ///////////////////////////////////////////////////////////////////////////////////
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public User findByIdAndUpdate(@PathVariable UUID id, @RequestBody UserDTO eventDTO) {
        return userSRV.findByIdAndUpdate(id, eventDTO);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        userSRV.deleteById(id);
    }

    @PatchMapping("/addMeCourse/{id}")
    public void newCourse(@PathVariable UUID id, @AuthenticationPrincipal User currentAuthenticatedUser) {
        userSRV.newCourse(id, currentAuthenticatedUser);
    }

    @PostMapping("/completeCourses/{id}")
    public void completeInternalCourses(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        userSRV.completeInternalCourses(id, user);
    }

    @PostMapping("/completeTask/{id}")
    public void completeTask(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        userSRV.completeTask(id, user);
    }

    @PatchMapping("/addRole/{id}")
    public void addRole(@PathVariable UUID id,
                        @RequestParam(name = "param") String param) {
        userSRV.addRole(id, param);
    }

    @PatchMapping("/removeRole/{id}")
    public void removeRole(@PathVariable UUID id,
                           @RequestParam(name = "param") String param) {
        userSRV.removeRole(id, param);
    }


}
