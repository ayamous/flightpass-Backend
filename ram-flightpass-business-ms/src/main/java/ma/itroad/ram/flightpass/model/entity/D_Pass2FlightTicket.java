package ma.itroad.ram.flightpass.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ma.itroad.core.model.entity.AuditEntity;
import ma.itroad.ram.flightpass.model.enums.CouponStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.pass2FlightTicket_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "d_Pass2FlightTicket",schema = "flightpass")
public class D_Pass2FlightTicket extends AuditEntity {

    private String pnrPass;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private D_FlightPassCustomerOrder fpOrder;
    private int couponNumber;
    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private FlightReservation flightReservation;

}
