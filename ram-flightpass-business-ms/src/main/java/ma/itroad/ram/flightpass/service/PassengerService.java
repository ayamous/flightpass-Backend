package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.PassengerBean;
import ma.itroad.ram.flightpass.model.entity.D_Passenger;

import java.util.List;

public interface PassengerService {

    D_Passenger createPassenger(PassengerBean passengerBean);

    List<PassengerBean> getPassengerByUserId();
}
