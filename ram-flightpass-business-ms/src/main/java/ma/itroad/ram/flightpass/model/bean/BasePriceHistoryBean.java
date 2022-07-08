package ma.itroad.ram.flightpass.model.bean;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BasePriceHistoryBean implements Serializable {

    private String departureAirportCode;
    private String arrivalAirportCode;
    private double amount;
    private LocalDate date;
    private String currencyCode;


}
