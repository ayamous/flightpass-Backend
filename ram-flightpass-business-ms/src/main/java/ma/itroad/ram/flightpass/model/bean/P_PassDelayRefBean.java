package ma.itroad.ram.flightpass.model.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class P_PassDelayRefBean {
    private int nbrMonths;
    private double percentage;
    private List<Long> flightPassConfigs_Id;
}
