package ma.itroad.ram.flightpass.model.entity;

import lombok.*;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.lPFPDailyOffer") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "g_LPFPDailyOffer",schema = "flightpass")
public class G_LPFPDailyOffer extends AuditEntity {

    private LocalDate fpDailyOfferDate;
    @ManyToOne
    private P_CurrencyRef fpCurrencyRef;
    @ManyToOne
    private P_SegmentRef flySegmentRef;

    private double fpCalculatedBeginningPrice;

    private double fpAmadeusPriceAmountBase;

    private double fpAmadeusTaxAmountBase;

    private int fpPassNumberBase;

    private int fpDateToTravelBase;

    private int fpPeriodBase;

}
