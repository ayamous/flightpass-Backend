package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.LandingPageConfigBean;
import ma.itroad.ram.flightpass.model.bean.P_FlightPassConfigBean;

import java.util.List;

public interface P_FlightPassConfigService {
    P_FlightPassConfigBean postFlightPassConfig (String reference ,Long localAirportRef_Id );
    List<P_FlightPassConfigBean> getFlightPassConfigs ();
    P_FlightPassConfigBean getFlightPassConfigById(Long configId);
    P_FlightPassConfigBean getFlightPassConfigByReference(String reference);
    P_FlightPassConfigBean patchFlightPassConfig(Long configId , Boolean enabled);
    LandingPageConfigBean getDefaultConfig(Long configId, Long segmentId, String currency);

}
