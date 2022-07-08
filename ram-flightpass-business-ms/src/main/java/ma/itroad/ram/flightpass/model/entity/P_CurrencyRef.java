package ma.itroad.ram.flightpass.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.p_currency_ref_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "p_currency_ref",schema = "flightpass")
public class P_CurrencyRef extends AuditEntity {

    private String code;
    private String name;

//    @OneToMany(mappedBy = "currencyRef")
//    private List<D_PriceBaseHistory> priceBaseHistories;
//
//    public P_CurrencyRef(String code, String name) {
//        this.code = code;
//        this.name = name;
//    }
}
