package ma.itroad.ram.flightpass.model.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BaseTaxHistoryBean {

    private String departureAirportCode;
    private String arrivalAirportCode;
    private double amount;
    private LocalDate date;
    private String currencyCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String flightFrom;


}
