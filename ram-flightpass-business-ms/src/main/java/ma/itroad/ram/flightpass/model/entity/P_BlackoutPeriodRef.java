package ma.itroad.ram.flightpass.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.p_BlackoutPeriodRef_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "p_BlackoutPeriodRef",schema = "flightpass")

public class P_BlackoutPeriodRef extends AuditEntity {

    private LocalDate startDate;
    private LocalDate endDate;
    @Column(unique = true)
    private String referenceBlackout;
    @ManyToOne
    private P_AirportRef arrivalAirport;

}
