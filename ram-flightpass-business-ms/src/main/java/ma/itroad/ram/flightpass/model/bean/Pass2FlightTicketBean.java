package ma.itroad.ram.flightpass.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pass2FlightTicketBean {

    private Long flightpassOrderId;
    private LocalDate reservationDate;
    private String reservationId;
    private String pnrPass;
    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String seatNumber;
    private int maxKg;
    private String mobileNumberReservation;
    private String emailReservation;


}
