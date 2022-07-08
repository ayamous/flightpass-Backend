package ma.itroad.ram.flightpass.service.impl;

import ma.itroad.ram.flightpass.model.bean.*;
import ma.itroad.ram.flightpass.model.entity.D_FlightPassCustomerOrder;
import ma.itroad.ram.flightpass.model.entity.D_Passenger;
import ma.itroad.ram.flightpass.model.entity.P_CurrencyRef;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import ma.itroad.ram.flightpass.model.enums.FpStatus;
import ma.itroad.ram.flightpass.model.enums.FpType;
import ma.itroad.ram.flightpass.model.mapper.FPCustomerOrderMapper;
import ma.itroad.ram.flightpass.model.mapper.FlightPassOverviewMapper;
import ma.itroad.ram.flightpass.repository.CurrencyRepository;
import ma.itroad.ram.flightpass.repository.D_Pass2FlightTicketRepository;
import ma.itroad.ram.flightpass.repository.FlightPassCustomerOrderRepository;
import ma.itroad.ram.flightpass.repository.SegmentRepository;
import ma.itroad.ram.flightpass.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FlightPassCustomerOrderServiceImpl implements FlightPassCustomerOrderService {


    @Autowired
    private PassengerService passengerService;
    @Autowired
    private FPCustomerOrderMapper fpCustomerOrderMapper;
    @Autowired
    private SegmentRepository segmentRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private FlightPassCustomerOrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Value("${guest.id}")
    private String guestId;
    @Autowired
    private D_Pass2FlightTicketRepository ticketRepository;

    @Autowired
    private Pass2FlightTicketService ticketService;

    @Autowired
    private FlightPassOverviewMapper flightPassOverviewMapper;

    @Autowired
    private FlightPassService flightPassService;

    @Override
    public FPCustomerOrderBean createOrder(FPCustomerOrderBean fpCustomerOrderBean) {


        Optional<P_SegmentRef> segmentRefOptional = segmentRepository.findById(fpCustomerOrderBean.getSegmentId());
        P_CurrencyRef pCurrencyRef = currencyRepository.findByCode(fpCustomerOrderBean.getCurrency());


        if (!segmentRefOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found for this Id");
        }

        if (pCurrencyRef == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "currency not found for this Code");
        }

        FlightPassPriceRequestBean flightPassPriceRequestBean = new FlightPassPriceRequestBean(
                fpCustomerOrderBean.getSegmentId(),fpCustomerOrderBean.getCurrency(),null,fpCustomerOrderBean.getBaseConfig(),
                fpCustomerOrderBean.getDayToTravel(),fpCustomerOrderBean.getPassDelay());


        FlightPassBean flightPassBean = flightPassService.calculateFlightPassPrice(flightPassPriceRequestBean);

        LocalDate expiryDate = LocalDate.now().plusMonths(fpCustomerOrderBean.getPassDelay());


        String userAccountId = userService.getUserAccountId();


        D_FlightPassCustomerOrder passCustomerOrder = new D_FlightPassCustomerOrder(
                LocalDate.now(),
                pCurrencyRef, segmentRefOptional.get(),
                flightPassBean.getFlightpassPrice(), flightPassBean.getBasePrice(),
                flightPassBean.getBaseTaxe(), fpCustomerOrderBean.getBaseConfig(), fpCustomerOrderBean.getDayToTravel(),
                fpCustomerOrderBean.getPassDelay(), expiryDate, userAccountId, fpCustomerOrderBean.getDeviceId(),
                null, FpStatus.CREATED, FpType.SUBSCRIPTION);

        String configuredBy = userAccountId == null ? "GUEST" : "USER";

        passCustomerOrder.setCreatedBy(configuredBy);

        orderRepository.save(passCustomerOrder);

        return fpCustomerOrderMapper.toBean(passCustomerOrder);

    }


    @Override
    public FPCustomerOrderBean createOrder(PassengerBean passengerBean, String deviceId) {


        String userAccountId = userService.getUserAccountId();
        if (userAccountId == null || userAccountId.equals(guestId)) {
            return null;
        }

        D_FlightPassCustomerOrder lastConfigOrderRepo = getLastConfigOrderRepo(deviceId);

        D_Passenger passenger = passengerService.createPassenger(passengerBean);

        lastConfigOrderRepo.setFpPassengerRef(passenger);
        lastConfigOrderRepo.setFpUserRef(userAccountId);
        lastConfigOrderRepo.setUpdatedBy(userAccountId);

        //test Payment
        lastConfigOrderRepo.setFpStatus(FpStatus.PAID);
        ticketService.createNewTickets(lastConfigOrderRepo);
        //test Payment

        return fpCustomerOrderMapper.toBean(orderRepository.save(lastConfigOrderRepo));
    }

    @Override
    public FPCustomerOrderBean getLastOrderConfig(String deviceId) {

        String userAccountId = userService.getUserAccountId();
        if (userAccountId == null || userAccountId.equals(guestId)) {
            return null;
        }

        D_FlightPassCustomerOrder order = getLastConfigOrderRepo(deviceId);


        if (order == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Vous n'avez encore configurer votre Pass");
        }


        return fpCustomerOrderMapper.toBean(orderRepository.save(order));
    }

    @Override
    public Page<FlightPassOverviewBean> getFlightPassesByUserId(Pageable pageable, FpStatus status) {
        String userAccountId = userService.getUserAccountId();
        return orderRepository.findByFpUserRefAndFpStatus(userAccountId,status,pageable)
                .map(order -> flightPassOverviewMapper.toBean(order,new FlightPassOverviewBean()));

    }

    private D_FlightPassCustomerOrder getLastConfigOrderRepo(String deviceId) {
        return orderRepository.
                findTopByFpOrderDateAndDeviceIdOrderByCreatedOnDesc(LocalDate.now(), deviceId);
    }


}
