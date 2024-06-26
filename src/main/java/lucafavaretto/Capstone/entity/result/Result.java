package lucafavaretto.Capstone.entity.result;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucafavaretto.Capstone.auth.user.User;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private LocalDate date;
    private String title;
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Result(String title, String description, LocalDate date, User user) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.user = user;

    }
}
