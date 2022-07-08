package ma.itroad.ram.flightpass.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandingPageConfigBean {

    private P_BaseConfigRefBean baseConfig;
    private P_DayToDepartureRefBean dayToDeparture;
    private P_PassDelayRefBean passDelay;
    private String segment;
    private String currency;
    private double flightpassPrice;
}
