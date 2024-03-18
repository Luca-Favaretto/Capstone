package lucafavaretto.Capstone.entity.task;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserSRV;
import lucafavaretto.Capstone.entity.presence.Presence;

import lucafavaretto.Capstone.exceptions.BadRequestException;
import lucafavaretto.Capstone.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskSRV {
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    UserSRV userSRV;

    public Page<Task> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return taskDAO.findAll(pageable);
    }

    public Task findById(UUID id) {
        return taskDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(()
                -> new NotFoundException(String.valueOf(id)));
    }

    public Task findByIdAndUpdate(UUID id, TaskDTO taskDTO) {
        Task found = findById(id);
        User userFound = userSRV.findById(UUID.fromString(taskDTO.userId()));

        found.setTitle(taskDTO.title());
        found.setDescription(taskDTO.description());
        found.setExpirationDate(taskDTO.expirationDate());
        found.setUser(userFound);

        return taskDAO.save(found);
    }

    public Task save(TaskDTO taskDTO) {
        User userFound = userSRV.findById(UUID.fromString(taskDTO.userId()));
        return taskDAO.save(new Task(taskDTO.title(), taskDTO.description(), taskDTO.expirationDate(), userFound));
    }

    public void deleteById(UUID id) {
        Task found = findById(id);
        taskDAO.delete(found);
    }

    public Page<Task> findByUser(int pageNumber, int pageSize, String orderBy, User user) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));

        return taskDAO.findByUser(pageable, userSRV.findById(user.getId()));
    }

}
