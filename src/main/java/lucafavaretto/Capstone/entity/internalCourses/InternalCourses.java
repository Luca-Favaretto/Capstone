package lucafavaretto.Capstone.entity.internalCourses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucafavaretto.Capstone.auth.user.User;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "internal_courses")
public class InternalCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String title;
    private String description;
    private int hours;

    @ManyToMany(mappedBy = "internalCourses")
    private Set<User> users = new LinkedHashSet<>();


    public InternalCourses(String title, String description, int hours) {
        this.title = title;
        this.description = description;
        this.hours = hours;
    }

}
