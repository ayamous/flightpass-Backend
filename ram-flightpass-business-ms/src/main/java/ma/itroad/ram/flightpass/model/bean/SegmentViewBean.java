package ma.itroad.ram.flightpass.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SegmentViewBean {

    private String segmentId;
    private String configId;
    private AirportBean departureAirportRef;
    private AirportBean arrivalAirportRef;
}
