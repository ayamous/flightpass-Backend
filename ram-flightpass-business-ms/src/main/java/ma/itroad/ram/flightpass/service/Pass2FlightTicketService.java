package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.Pass2FlightTicketBean;
import ma.itroad.ram.flightpass.model.entity.D_FlightPassCustomerOrder;

import java.util.List;

public interface Pass2FlightTicketService {

    Pass2FlightTicketBean book(Pass2FlightTicketBean flightTicketBean);
    Pass2FlightTicketBean createNewTickets(D_FlightPassCustomerOrder order);
    List<Pass2FlightTicketBean> getFlightPassDetails(Long id);
    void delete(Long id);
}
