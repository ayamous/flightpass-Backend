package ma.itroad.ram.flightpass.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.p_PassDelayRef_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "p_PassDelayRef",schema = "flightpass")
public class P_PassDelayRef extends AuditEntity {
    private int nbrMonths;
    private double percentage;
    @ManyToMany(mappedBy = "p_passDelayRefs")
    Set<P_FlightPassConfig> flightPassConfigs;

    public P_PassDelayRef(int nbrMonths, double percentage) {
        this.nbrMonths = nbrMonths;
        this.percentage = percentage;
    }
}
