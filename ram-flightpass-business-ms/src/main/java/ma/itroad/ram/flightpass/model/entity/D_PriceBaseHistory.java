package ma.itroad.ram.flightpass.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.priceBaseHistory_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "d_PriceBaseHistory",schema = "flightpass")
public class D_PriceBaseHistory extends AuditEntity {

    @Column(updatable = false)
    private double amount;

    @Column(name = "price_date")
    private LocalDate date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private P_SegmentRef segment;
    @ManyToOne
    private P_CurrencyRef currencyRef;


    public D_PriceBaseHistory(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }
}

