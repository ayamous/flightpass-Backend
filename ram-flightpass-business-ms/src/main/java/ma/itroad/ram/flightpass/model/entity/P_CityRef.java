package ma.itroad.ram.flightpass.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.p_city_ref_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "p_city_ref",schema = "flightpass")
public class P_CityRef extends AuditEntity {

    private String name;
    @ManyToOne(cascade ={
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
    })
    private P_CountryRef country;
}
