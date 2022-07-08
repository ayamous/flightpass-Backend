package ma.itroad.ram.flightpass.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.Pass2FlightTicketBean;
import ma.itroad.ram.flightpass.service.Pass2FlightTicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/business/pass2FlightTicket")
public class Pass2FlightTicketController {

    private Pass2FlightTicketService ticketService;


    @PostMapping("/create")
    public void bookFlight(@RequestBody Pass2FlightTicketBean bean) {
        ticketService.book(bean);
    }

    @PostMapping("/get/{id}")
    public List<Pass2FlightTicketBean> getFlights(@PathVariable Long id) {
        return ticketService.getFlightPassDetails(id);
    }
}
