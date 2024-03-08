package lucafavaretto.Capstone.auth.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucafavaretto.Capstone.entity.contract.Contract;
import lucafavaretto.Capstone.entity.internalCourses.InternalCourses;
import lucafavaretto.Capstone.entity.presence.Presence;
import lucafavaretto.Capstone.entity.result.Result;
import lucafavaretto.Capstone.entity.role.Role;
import lucafavaretto.Capstone.entity.task.Task;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties({"password", "credentialsNonExpired", "accountNonExpired", "authorities", "accountNonLocked", "enabled", "internalCourses", "presences", "results", "task"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private int rating;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToOne(mappedBy = "user")
    private Contract contract;

    @OneToMany(mappedBy = "user")
    private Set<Presence> presences;

    @OneToMany(mappedBy = "user")
    private Set<Result> results;

    @OneToMany(mappedBy = "user")
    private Set<Task> task;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_internalCourses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "internalCourses_id"))
    private Set<InternalCourses> internalCourses = new LinkedHashSet<>();

    public User(String name, String surname, String username, String password, String email, String avatar) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;

        this.rating = 7;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void addCourse(InternalCourses courses) {
        this.internalCourses.add(courses);
    }

    public void removeCourses(InternalCourses courses) {
        this.internalCourses.remove(courses);
        courses.getUsers().remove(this);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
