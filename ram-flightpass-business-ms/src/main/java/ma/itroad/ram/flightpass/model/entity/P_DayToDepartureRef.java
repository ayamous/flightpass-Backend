package ma.itroad.ram.flightpass.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.P_DayToDepartureRef_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "P_DayToDepartureRef",schema = "flightpass",
        uniqueConstraints={@UniqueConstraint(columnNames={"nbr_days","percentage"})})

public class P_DayToDepartureRef extends AuditEntity {

    @Column(name = "nbr_days")
    private int nbrDays;
    @Column(name = "percentage")
    private double percentage;
    @ManyToMany(mappedBy = "p_dayToDepartureRefs")
    Set<P_FlightPassConfig> flightPassConfigs;
    public P_DayToDepartureRef(int nbrDays, double percentage) {
        this.nbrDays = nbrDays;
        this.percentage = percentage;
    }
}
