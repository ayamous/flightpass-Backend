package ma.itroad.ram.flightpass.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTaxRequestByDirectionBean {
    private String departureAirportCode;
    private String arrivalAirportCode;
    private String currencyCode;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
}
