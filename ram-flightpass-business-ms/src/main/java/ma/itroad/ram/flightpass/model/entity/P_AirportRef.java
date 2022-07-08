package ma.itroad.ram.flightpass.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.airport_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "p_airport_ref",schema = "flightpass")
public class P_AirportRef extends AuditEntity {

    private String name;
    @Column(unique = true)
    private String code;
    private String latitude;
    private String longitude;
    @ManyToOne(cascade ={
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
    })
    private P_CityRef city;
    @OneToMany(mappedBy = "arrivalAirport")
    private List<P_BlackoutPeriodRef> p_blackoutPeriodRefs;


    public P_AirportRef(String name, String code, String latitude, String longitude, P_CityRef city) {
        this.name = name;
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }
}
