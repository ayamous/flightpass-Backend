package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.P_BlackoutPeriodRefBean;

import java.util.List;

public interface P_BlackoutPeriodRefService {
    P_BlackoutPeriodRefBean createBlackoutPeriod(P_BlackoutPeriodRefBean blackoutPeriod);
    List<P_BlackoutPeriodRefBean> getBlackoutPeriodOneWay(String airportCode);
    List<P_BlackoutPeriodRefBean> getBlackoutPeriodRoundTrip(String airportCode1,String airportCode2);
    List<P_BlackoutPeriodRefBean> getAllBlackoutPeriod();
    P_BlackoutPeriodRefBean updateBlackoutPeriod(P_BlackoutPeriodRefBean blackoutPeriod, Long id);
    void deleteBlackoutPeriod(Long id);

}
