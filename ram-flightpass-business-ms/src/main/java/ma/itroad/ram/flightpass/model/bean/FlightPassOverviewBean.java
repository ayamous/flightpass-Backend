package ma.itroad.ram.flightpass.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FlightPassOverviewBean {

    private Long orderId;
    private String fullNamePassenger;
    private int consumedCoupons;
    private int remainingCoupons;
    private String departureAirport;
    private String departureCity;
    private String departureAirportCode;
    private String arrivalAirport;
    private String arrivalCity;
    private String arrivalAirportCode;
    private int dayToTravel;
    private LocalDate expiryDate;
    private LocalDate orderDate;
    private double price;
}
