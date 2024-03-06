package lucafavaretto.Capstone.entity.internalCourses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InternalCoursesDAO extends JpaRepository<InternalCourses, UUID> {
    boolean existsByTitle(String name);
}
