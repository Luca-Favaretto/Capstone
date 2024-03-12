package lucafavaretto.Capstone.entity.result;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserDAO;
import lucafavaretto.Capstone.auth.user.UserSRV;
import lucafavaretto.Capstone.entity.internalCourses.InternalCourses;
import lucafavaretto.Capstone.entity.internalCourses.InternalCoursesDAO;
import lucafavaretto.Capstone.entity.internalCourses.InternalCoursesSRV;
import lucafavaretto.Capstone.entity.task.Task;
import lucafavaretto.Capstone.entity.task.TaskDAO;
import lucafavaretto.Capstone.entity.task.TaskSRV;
import lucafavaretto.Capstone.enums.AbstinenceStatus;
import lucafavaretto.Capstone.exceptions.BadRequestException;
import lucafavaretto.Capstone.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ResultSRV {
    @Autowired
    ResultDAO resultDAO;
    @Autowired
    UserDAO userDAO;


    public Page<Result> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return resultDAO.findAll(pageable);
    }

    public Result findById(UUID id) {
        return resultDAO.findById(UUID.fromString(String.valueOf(id)))
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Result save(ResultDTO resultDTO, User user) {
        if (resultDAO.existsByUserAndTitle(user, resultDTO.title()))
            throw new BadRequestException("Result already exist");
        Result result = new Result(resultDTO.title(), resultDTO.description(), user);
        result.setDate(LocalDate.now());
        return resultDAO.save(result);
    }


    public Result findByIdAndUpdate(UUID id, ResultDTO resultDTO) {
        Result found = findById(id);
        found.setTitle(resultDTO.title());
        found.setDescription(resultDTO.description());
        return resultDAO.save(found);
    }

    public void deleteById(UUID id) {
        Result found = findById(id);
        resultDAO.delete(found);
    }

    public Page<Result> findByUser(int pageNumber, int pageSize, String orderBy, User user) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        User found = userDAO.findById(user.getId()).orElseThrow(() -> new NotFoundException(user.getId()));
        return resultDAO.findByUser(pageable, found);
    }


}
