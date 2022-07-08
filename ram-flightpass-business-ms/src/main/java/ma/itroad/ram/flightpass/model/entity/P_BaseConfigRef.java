package ma.itroad.ram.flightpass.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import java.util.Set;


@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.p_BaseConfigRef_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "p_BaseConfigRef",schema = "flightpass")
public class P_BaseConfigRef extends AuditEntity {
    private int nbrFlights;
    private double percentage;
    @ManyToMany(mappedBy = "p_baseConfigRefs")
    Set<P_FlightPassConfig> flightPassConfigs;

    public P_BaseConfigRef(int nbrFlights, double percentage) {
        this.nbrFlights = nbrFlights;
        this.percentage = percentage;
    }

}
