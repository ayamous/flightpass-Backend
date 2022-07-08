package ma.itroad.ram.flightpass.model.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AirportBean implements Serializable {

    private String name;
    private String code;
    private String latitude;
    private String longitude;
    private String city;
    private String country;
    private String isoCode;
}
