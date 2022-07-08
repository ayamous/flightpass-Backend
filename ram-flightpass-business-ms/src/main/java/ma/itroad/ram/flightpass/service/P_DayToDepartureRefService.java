package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.P_DayToDepartureRefBean;

import java.util.List;

public interface P_DayToDepartureRefService {
    P_DayToDepartureRefBean postDayToDepartureRef(Long configId, int nbrDays, double percentage);
    List<P_DayToDepartureRefBean>getDayToDepartureRef();
    List<P_DayToDepartureRefBean> getDayToDepartureRefByConfigId(Long configId);
    P_DayToDepartureRefBean getMaxDayToDeparture(Long configId);
    P_DayToDepartureRefBean getMinDayToDeparture(Long configId);
    List<P_DayToDepartureRefBean> getDayToDepartureRefByNbrDays(int days);
    P_DayToDepartureRefBean getDayToDepartureRefByNbrDaysAndConfigId(Long configId ,int days);
    P_DayToDepartureRefBean patchDayToDepartureRef(Long configId , int nbrDays ,double percentage);
    void deleteDayToDepartureRefByNbrDaysAndConfigId(Long configId , int nbrDays);





















    //List<Integer> getDaysToDeparture();
    //void deleteDayToDepartureRef(int nbrFlight);
}
