package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.FPCustomerOrderBean;
import ma.itroad.ram.flightpass.model.bean.FlightPassOverviewBean;
import ma.itroad.ram.flightpass.model.bean.PassengerBean;
import ma.itroad.ram.flightpass.model.enums.FpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightPassCustomerOrderService {

    FPCustomerOrderBean createOrder(FPCustomerOrderBean fpCustomerOrderBean);

    FPCustomerOrderBean createOrder(PassengerBean passengerBean, String deviceId);

    FPCustomerOrderBean getLastOrderConfig(String deviceId);

    Page<FlightPassOverviewBean> getFlightPassesByUserId(Pageable pageable, FpStatus status);


}
