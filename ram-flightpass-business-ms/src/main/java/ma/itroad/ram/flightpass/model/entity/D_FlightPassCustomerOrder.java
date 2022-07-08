package ma.itroad.ram.flightpass.model.entity;

import lombok.*;
import ma.itroad.core.model.entity.AuditEntity;
import ma.itroad.ram.flightpass.model.enums.FpStatus;
import ma.itroad.ram.flightpass.model.enums.FpType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.flightPassCustomerOrder_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "flightPassCustomerOrder",schema = "flightpass")
public class D_FlightPassCustomerOrder extends AuditEntity {

    private LocalDate fpOrderDate;
    @ManyToOne
    private P_CurrencyRef fpCurrencyRef;
    @ManyToOne
    private P_SegmentRef flySegmentRef;
    private double fpCalculatedFinalPrice;
    private double fpAmadeusPriceAmount;
    private double fpAmadeusTaxAmount;
    private int fpPassNumber;
    private int fpDTD;
    private int fpPeriod;
    private LocalDate fpExpiryDate;
    private String fpUserRef;
    private String deviceId;
    @ManyToOne
    D_Passenger fpPassengerRef;
    @Enumerated(EnumType.STRING)
    private FpStatus fpStatus;
    @Enumerated(EnumType.STRING)
    private FpType fpType;
}
