package lucafavaretto.Capstone.auth.user;


import lucafavaretto.Capstone.configuration.EmailSender;
import lucafavaretto.Capstone.entity.internalCourses.InternalCourses;
import lucafavaretto.Capstone.entity.internalCourses.InternalCoursesDAO;
import lucafavaretto.Capstone.entity.internalCourses.InternalCoursesSRV;
import lucafavaretto.Capstone.entity.result.Result;
import lucafavaretto.Capstone.entity.result.ResultDTO;
import lucafavaretto.Capstone.entity.result.ResultSRV;
import lucafavaretto.Capstone.entity.role.Role;
import lucafavaretto.Capstone.entity.role.RoleSRV;
import lucafavaretto.Capstone.entity.task.Task;
import lucafavaretto.Capstone.entity.task.TaskDAO;
import lucafavaretto.Capstone.entity.task.TaskSRV;
import lucafavaretto.Capstone.exceptions.BadRequestException;
import lucafavaretto.Capstone.exceptions.NotFoundException;
import lucafavaretto.Capstone.exceptions.UnauthorizedException;
import org.hibernate.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserSRV {
    @Autowired
    UserDAO userDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    InternalCoursesDAO internalCoursesDAO;
    @Autowired
    RoleSRV roleSRV;
    @Autowired
    ResultSRV resultSRV;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    InternalCoursesSRV internalCoursesSRV;
    @Autowired
    EmailSender emailSender;

    public Page<User> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User findById(UUID id) {
        return userDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public User save(UserDTO userDTO) throws IOException {
        if (userDAO.existsByEmail(userDTO.email())) throw new BadRequestException("email already exist");
        User user = new User(userDTO.name(), userDTO.surname(), userDTO.username(), passwordEncoder.encode(userDTO.password()), userDTO.email(), userDTO.name() + userDTO.surname(), userDTO.rating());
        user.addRole(roleSRV.findByRole("USER"));
        //    emailSender.sendRegistrationEmail(userDTO);
        return userDAO.save(user);
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public User findByIdAndUpdate(UUID id, UserDTO userDTO) {
        User found = findById(UUID.fromString(String.valueOf(id)));
        found.setName(userDTO.name());
        found.setSurname(userDTO.surname());
        found.setUsername(userDTO.username());
        found.setPassword(userDTO.password());
        found.setEmail(userDTO.email());
        found.setRating(userDTO.rating());
        return userDAO.save(found);
    }

    public void deleteById(UUID id) {
        User found = findById(id);
        userDAO.delete(found);
    }

    public void newCourse(UUID id, User user) {
        InternalCourses courses = internalCoursesSRV.findById(id);
        user.addCourse(courses);
        userDAO.save(user);
    }


    public void completeInternalCourses(UUID id, User user) {
        InternalCourses found = internalCoursesSRV.findById(id);
        ResultDTO resultDTO = new ResultDTO(found.getTitle(), "Internal course during " + found.getHours() + " hours");
        User foundUser = findById(user.getId());
        foundUser.removeCourses(found);
        userDAO.save(foundUser);
        resultSRV.save(resultDTO, foundUser);
    }

    public void completeTask(UUID id, User user) {
        Task found = taskDAO.findById(id).orElseThrow(() -> new NotFoundException("task don't found"));
        ResultDTO resultDTO = new ResultDTO(found.getTitle() + LocalDate.now(), "Task description : " + found.getDescription());
        taskDAO.delete(found);
        resultSRV.save(resultDTO, user);
    }

    public void addRole(UUID id, String name) {
        Role role = roleSRV.findByRole(name);
        User foundUser = findById(id);
        foundUser.addRole(role);
        userDAO.save(foundUser);
    }

    public void removeRole(UUID id, String name) {
        Role role = roleSRV.findByRole(name);
        User foundUser = findById(id);
        foundUser.removeRole(role);
        userDAO.save(foundUser);
    }
}
