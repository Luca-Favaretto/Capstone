package lucafavaretto.Capstone.entity.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.enums.ContractTypology;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ContractTypology contractTypology;
    private int weeklyHours;
    private double retribution;
    private LocalDate startingDate;
    private LocalDate finishDate;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Contract(ContractTypology contractTypology, int weeklyHours, double retribution, LocalDate startingDate, User user) {
        this.contractTypology = contractTypology;
        this.weeklyHours = weeklyHours;
        this.retribution = retribution;
        this.startingDate = startingDate;
        this.user = user;

    }
}
