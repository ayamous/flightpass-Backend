package ma.itroad.ram.flightpass.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.flightReservation_seq")})


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "flightReservation", schema = "flightpass")
public class FlightReservation extends AuditEntity {

    private String reservationID;
    private LocalDate reservationDate;
    private String flightNumber;
    @ManyToOne
    P_AirportRef departureAirportRef;
    @ManyToOne
    P_AirportRef arrivalAirportRef;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String seatNumber;
    private int maxKg;
    private String mobileNumberReservation;
    private String emailReservation;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "flightReservation")
    private D_Pass2FlightTicket ticket;


    public FlightReservation(String reservationID, D_Pass2FlightTicket ticket) {
        this.reservationID = reservationID;
        this.ticket = ticket;
    }
}
