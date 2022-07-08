package ma.itroad.ram.flightpass.model.bean;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class P_BaseConfigRefBean {
    private int nbrFlights;
    private double percentage;
    private List<Long> flightPassConfigs_Id;
}
