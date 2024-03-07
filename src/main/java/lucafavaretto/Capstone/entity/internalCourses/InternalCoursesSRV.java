package lucafavaretto.Capstone.entity.internalCourses;

import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserDTO;
import lucafavaretto.Capstone.entity.result.Result;
import lucafavaretto.Capstone.entity.result.ResultDTO;
import lucafavaretto.Capstone.entity.result.ResultSRV;
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
import java.util.UUID;

@Service
public class InternalCoursesSRV {
    @Autowired
    InternalCoursesDAO internalCoursesDAO;


    public Page<InternalCourses> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return internalCoursesDAO.findAll(pageable);
    }

    public InternalCourses findById(UUID id) {
        return internalCoursesDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public InternalCourses save(InternalCoursesDTO internalCoursesDTO) {
        if (internalCoursesDAO.existsByTitle(internalCoursesDTO.title()))
            throw new BadRequestException("title already exist");
        return internalCoursesDAO.save(new InternalCourses(internalCoursesDTO.title(), internalCoursesDTO.hours()));
    }


    public InternalCourses findByIdAndUpdate(UUID id, InternalCoursesDTO internalCoursesDTO) {
        InternalCourses found = findById(id);
        found.setTitle(internalCoursesDTO.title());
        found.setHours(internalCoursesDTO.hours());
        return internalCoursesDAO.save(found);
    }

    public void deleteById(UUID id) {
        InternalCourses found = findById(id);
        internalCoursesDAO.delete(found);
    }

}
