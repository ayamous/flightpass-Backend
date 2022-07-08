package ma.itroad.ram.flightpass.model.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class P_DayToDepartureRefBean {
    private int nbrDays;
    private double percentage;
    private List<Long> configIds;

}
