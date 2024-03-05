package lucafavaretto.Capstone.entity.presence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.enums.AbstinenceStatus;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "presence")
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private LocalDate date;
    private LocalDate startingHour;
    private LocalDate finishHour;
    @Enumerated(EnumType.STRING)
    private AbstinenceStatus abstinenceStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Presence(LocalDate date, LocalDate startingHour, User user) {
        this.date = date;
        this.startingHour = startingHour;
        this.user = user;
    }

    public Presence(LocalDate date, AbstinenceStatus abstinenceStatus, User user) {
        this.date = date;
        this.abstinenceStatus = abstinenceStatus;
        this.user = user;
    }
}
