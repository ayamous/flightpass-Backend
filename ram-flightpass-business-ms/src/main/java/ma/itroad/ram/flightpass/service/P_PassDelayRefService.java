package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.P_PassDelayRefBean;

import java.util.List;

public interface P_PassDelayRefService {
    P_PassDelayRefBean postPassDelayRef(Long configId, int nbrMonths, double percentage);
    List<P_PassDelayRefBean>getPassDelayRef();
    List<P_PassDelayRefBean> getPassDelayRefByConfigId(Long configId);
     P_PassDelayRefBean getMinPassDelay(Long configId);
     P_PassDelayRefBean getMaxPassDelay(Long configId);
    List<P_PassDelayRefBean> getPassDelayRefByNbrMonths(int nbrMonths);
    P_PassDelayRefBean getPassDelayRefByNbrMonthsAndConfigId(Long configId ,int nbrMonths);
    P_PassDelayRefBean patchPassDelayRefByConfigId(Long configId , int nbrMonths ,double percentage);
    void deletePassDelayRefByNbrMonthsAndConfigId(Long configId , int nbrMonths);
}
