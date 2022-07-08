package ma.itroad.ram.flightpass.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.taxBaseHistory_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "d_TaxBaseHistory",schema = "flightpass")
public class D_TaxBaseHistory extends AuditEntity {

    private double amount;
    @Column(name = "tax_date")
    private LocalDate date;
    private double percentage;

    @ManyToOne
    private P_SegmentRef segment;
    @ManyToOne
    private P_CurrencyRef currencyRef;

    @ManyToOne
    @JoinColumn(name = "flight_from")
    private P_AirportRef flightFrom;


}
