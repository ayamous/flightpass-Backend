package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.FlightPassBean;
import ma.itroad.ram.flightpass.model.bean.FlightPassPriceRequestBean;

public interface FlightPassService {

    FlightPassBean calculateFlightPassPrice(FlightPassPriceRequestBean passPriceBean);
}
