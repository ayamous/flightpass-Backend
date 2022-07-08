package ma.itroad.ram.flightpass.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class
FlightPassPriceRequestBean implements Serializable {
    private Long segmentId;
    private String currencyCode;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    private int nbrFlights = 1;
    private int dayToTravel = 0;
    private int passDelay = 0;

}
