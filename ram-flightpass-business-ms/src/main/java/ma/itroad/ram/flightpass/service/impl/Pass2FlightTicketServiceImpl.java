package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.Pass2FlightTicketBean;
import ma.itroad.ram.flightpass.model.entity.*;
import ma.itroad.ram.flightpass.model.enums.CouponStatus;
import ma.itroad.ram.flightpass.model.enums.FpStatus;
import ma.itroad.ram.flightpass.model.mapper.Pass2FlightTicketMapper;
import ma.itroad.ram.flightpass.repository.AirportRepository;
import ma.itroad.ram.flightpass.repository.D_Pass2FlightTicketRepository;
import ma.itroad.ram.flightpass.repository.FlightPassCustomerOrderRepository;
import ma.itroad.ram.flightpass.service.Pass2FlightTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class Pass2FlightTicketServiceImpl implements Pass2FlightTicketService {

    private D_Pass2FlightTicketRepository flightTicketRepository;
    private FlightPassCustomerOrderRepository flightPassCustomerOrderRepository;
    private AirportRepository airportRepository;
    private Pass2FlightTicketMapper flightTicketMapper;

    @Override
    public Pass2FlightTicketBean book(Pass2FlightTicketBean flightTicketBean) {

        Optional<D_FlightPassCustomerOrder> orderOptional = flightPassCustomerOrderRepository.
                findById(flightTicketBean.getFlightpassOrderId());


        if (!orderOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for Id : " + flightTicketBean);
        }

        //Check airport Exist
        P_AirportRef departureAirport = airportExist(flightTicketBean.getDepartureAirport());
        P_AirportRef arrivalAirport = airportExist(flightTicketBean.getArrivalAirport());

        if (departureAirport == null) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "airport not found for this code "
                    + flightTicketBean.getDepartureAirport());
        }

        if (arrivalAirport == null) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "airport not found for this code "
                    + flightTicketBean.getArrivalAirport());
        }

        D_FlightPassCustomerOrder fpOrder = orderOptional.get();


        //check if airport in the DTO should be the same airport existing in FP Order
        P_SegmentRef flySegmentRef = fpOrder.getFlySegmentRef();

        P_AirportRef airport1 = flySegmentRef.getDepartureAirportRef();
        P_AirportRef airport2 = flySegmentRef.getArrivalAirportRef();

        if (airport1 != departureAirport && airport2 != departureAirport) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "The airport "+departureAirport.getCode()+" isn't in the scope of this Flightpass");

        }
        if (airport1 != arrivalAirport && airport2 != arrivalAirport) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "The airport "+arrivalAirport.getCode()+" isn't in the scope of this Flightpass");

        }


        if (fpOrder.getFpStatus().equals(FpStatus.ISSUED)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "your flightpass is already consumed");
        }


        if (fpOrder.getFpStatus().equals(FpStatus.EXPIRED) || fpOrder.getFpExpiryDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "flightpass is Expired !!");
        }


        int couponsIssued = flightTicketRepository.countByFpOrderAndCouponStatus(fpOrder, CouponStatus.ISSUED);

        if (couponsIssued + 1 == fpOrder.getFpPassNumber()) {
            fpOrder.setFpStatus(FpStatus.ISSUED);
            flightPassCustomerOrderRepository.save(fpOrder);
        }

        List<D_Pass2FlightTicket> rawTickets = flightTicketRepository.
                findByFpOrderAndCouponStatus(fpOrder, CouponStatus.NEW);

        D_Pass2FlightTicket rawTicket = rawTickets.get(0);
        FlightReservation flightReservation = new FlightReservation(
                flightTicketBean.getReservationId(), flightTicketBean.getReservationDate(),
                flightTicketBean.getFlightNumber(),departureAirport,arrivalAirport,
                flightTicketBean.getDepartureDateTime(),flightTicketBean.getArrivalDateTime(),
                flightTicketBean.getSeatNumber(),flightTicketBean.getMaxKg(),
                flightTicketBean.getMobileNumberReservation(), flightTicketBean.getEmailReservation(),rawTicket
        );

        rawTicket.setCouponStatus(CouponStatus.ISSUED);
        rawTicket.setPnrPass(flightTicketBean.getPnrPass());
        rawTicket.setFlightReservation(flightReservation);

        return flightTicketMapper.toBean(flightTicketRepository.save(rawTicket));
    }

    @Override
    public Pass2FlightTicketBean createNewTickets(D_FlightPassCustomerOrder order) {


        int fpPassNumber = order.getFpPassNumber();
        flightTicketRepository.saveAll(IntStream.range(0, fpPassNumber)
                .mapToObj(value -> new D_Pass2FlightTicket(null, order,
                        fpPassNumber, CouponStatus.NEW, null))
                .collect(Collectors.toList()));
        return null;
    }

    @Override
    public List<Pass2FlightTicketBean> getFlightPassDetails(Long id) {
        Optional<D_FlightPassCustomerOrder> orderOptional = flightPassCustomerOrderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for Id : " + id);
        }

       return flightTicketRepository.findByFpOrderAndFlightReservationNotNull(orderOptional.get()).stream()
                .map(d_pass2FlightTicket -> flightTicketMapper.toBean(d_pass2FlightTicket)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        flightTicketRepository.deleteById(id);
    }


    private P_AirportRef airportExist(String airportCode) {
        return airportRepository.findByCode(airportCode);
    }
}
