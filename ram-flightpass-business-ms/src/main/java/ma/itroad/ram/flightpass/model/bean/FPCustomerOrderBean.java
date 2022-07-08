package ma.itroad.ram.flightpass.model.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FPCustomerOrderBean {

    PassengerBean passenger;

    @NotBlank
    private String currency;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private Long segmentId;
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String deviceId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double fpCalculatedFinalPrice;
    private int baseConfig;
    private int dayToTravel;
    private int passDelay;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate fpExpiryDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String airportDeparture;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String cityDeparture;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String airportArrival;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String cityArrival;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String configuredAs;

}
