package ma.itroad.ram.flightpass.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.p_segment_ref_seq") })

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "p_segment_ref", schema = "flightpass")

public class P_SegmentRef extends AuditEntity implements Serializable {


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departureAirportRef_id")
    private P_AirportRef departureAirportRef;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "arrivalAirportRef_id")
    private P_AirportRef arrivalAirportRef;




   @ManyToOne
    private P_FlightPassConfig p_flightPassConfig;


    @OneToMany(mappedBy = "segment", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<D_PriceBaseHistory> priceBaseHistories;

    @OneToMany(mappedBy = "segment", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<D_TaxBaseHistory> taxBaseHistories;


    public P_SegmentRef(P_AirportRef departureAirportRef, P_AirportRef arrivalAirportRef, P_FlightPassConfig p_flightPassConfig) {
        this.departureAirportRef = departureAirportRef;
        this.arrivalAirportRef = arrivalAirportRef;
        this.p_flightPassConfig = p_flightPassConfig;
    }
}



