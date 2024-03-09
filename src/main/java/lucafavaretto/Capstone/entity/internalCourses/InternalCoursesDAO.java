package lucafavaretto.Capstone.entity.internalCourses;

import lucafavaretto.Capstone.auth.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InternalCoursesDAO extends JpaRepository<InternalCourses, UUID> {
    boolean existsByTitle(String name);

    @Query("SELECT i FROM InternalCourses i JOIN i.users u ON u = :user LEFT JOIN u.results r ON (r.title IS NULL OR r.title != i.title)")
    Page<InternalCourses> findNotCompletedInternalCourses(Pageable pageable, User user);


}
