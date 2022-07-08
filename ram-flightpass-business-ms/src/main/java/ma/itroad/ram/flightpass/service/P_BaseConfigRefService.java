package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.P_BaseConfigRefBean;

import java.util.List;

public interface P_BaseConfigRefService {

    P_BaseConfigRefBean postBaseConfigRef(Long configId, int nbrFlights, double percentage);
    List<P_BaseConfigRefBean>getBaseConfigRef();
    List<P_BaseConfigRefBean> getBaseConfigRefByConfigId(Long configId);
    P_BaseConfigRefBean maxBaseConfig(Long configId);
    P_BaseConfigRefBean minBaseConfig(Long configId);
    List<P_BaseConfigRefBean> getBaseConfigRefByNbrFlights(int nbrFlights);
    P_BaseConfigRefBean getBaseConfigRefByNbrFlightsAndConfigId(Long configId ,int nbrFlights);
    P_BaseConfigRefBean patchBaseConfigRef(Long configId , int nbrFlights ,double percentage);
    void deleteBaseConfigRefByNbrFlightsAndConfigId(Long configId , int nbrFlights);

}
