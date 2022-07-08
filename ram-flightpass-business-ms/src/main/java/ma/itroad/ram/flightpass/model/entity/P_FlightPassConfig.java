package ma.itroad.ram.flightpass.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.p_FlightPassConfig_seq")})


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "p_FlightPassConfig", schema = "flightpass")


public class P_FlightPassConfig extends AuditEntity {
    @Column(unique = true)
    private String reference;
    //    @Column(columnDefinition = "boolean default true")
    private boolean enabled;
    private Date effectiveDate;
    @ManyToOne
    private P_AirportRef p_airportRef;
    @ManyToMany
    @JoinTable(
            schema = "FLIGHTPASS", name = "config_day_departure", joinColumns =
    @JoinColumn(name = "CONFIG_ID"), inverseJoinColumns = @JoinColumn(name = "DEPARTUREDAY_ID"))
    private Set<P_DayToDepartureRef> p_dayToDepartureRefs;
    @ManyToMany
    @JoinTable(
            schema = "FLIGHTPASS", name = "config_NBRFLIGHTS", joinColumns =
    @JoinColumn(name = "CONFIG_ID"), inverseJoinColumns = @JoinColumn(name = "NBRFLIGHTS_ID"))
    private Set<P_BaseConfigRef> p_baseConfigRefs;
    @ManyToMany
    @JoinTable(
            schema = "FLIGHTPASS", name = "config_delay", joinColumns =
    @JoinColumn(name = "CONFIG_ID"), inverseJoinColumns = @JoinColumn(name = "delay_ID"))
    private Set<P_PassDelayRef> p_passDelayRefs;
    @ManyToMany
    @JoinTable(
            schema = "FLIGHTPASS", name = "config_blackout", joinColumns =
    @JoinColumn(name = "CONFIG_ID"), inverseJoinColumns = @JoinColumn(name = "blackouts_ID"))

    private Set<P_BlackoutPeriodRef> p_blackoutPeriodRefs;

    public P_FlightPassConfig(String reference, P_AirportRef p_airportRef) {
        this.reference = reference;
        this.p_airportRef = p_airportRef;
        this.effectiveDate = new Date();
        this.enabled = true;
    }


}
