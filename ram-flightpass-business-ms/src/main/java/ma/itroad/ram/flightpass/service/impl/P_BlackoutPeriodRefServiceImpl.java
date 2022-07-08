package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.P_BlackoutPeriodRefBean;
import ma.itroad.ram.flightpass.model.entity.P_AirportRef;
import ma.itroad.ram.flightpass.model.entity.P_BlackoutPeriodRef;
import ma.itroad.ram.flightpass.model.mapper.P_BlackoutPeriodRefMapper;
import ma.itroad.ram.flightpass.repository.AirportRepository;
import ma.itroad.ram.flightpass.repository.P_BlackoutPeriodRefRepository;
import ma.itroad.ram.flightpass.service.P_BlackoutPeriodRefService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class P_BlackoutPeriodRefServiceImpl implements P_BlackoutPeriodRefService {

    private AirportRepository airportRepository;
    private P_BlackoutPeriodRefMapper blackoutPeriodMapper;
    private P_BlackoutPeriodRefRepository blackoutPeriodRepository;

    @Override
    public P_BlackoutPeriodRefBean createBlackoutPeriod(P_BlackoutPeriodRefBean blackoutPeriod) {

        Optional<P_AirportRef> optionalAirport = getOptionalP_airportRef(blackoutPeriod.getAirportId());

        boolean dateCheck = isBeforeOrEqual(blackoutPeriod.getStartDate(), blackoutPeriod.getEndDate());
        P_BlackoutPeriodRef referenceBlackout = blackoutPeriodRepository.findByReferenceBlackout(blackoutPeriod.getReferenceBlackout());

        if (referenceBlackout != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "A Blackout with reference " + blackoutPeriod.getReferenceBlackout() + " already Exist");
        }

        if (!dateCheck) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the Start date is greater than end Date");
        }

        P_BlackoutPeriodRef blackoutPeriodRef = blackoutPeriodMapper.beanToEntity(blackoutPeriod);
        blackoutPeriodRef.setArrivalAirport(optionalAirport.get());


        return blackoutPeriodMapper.entityToBean(blackoutPeriodRepository.save(blackoutPeriodRef));
    }


    @Override
    public List<P_BlackoutPeriodRefBean> getBlackoutPeriodOneWay(String airportCode) {
        P_AirportRef airportRef = airportRepository.findByCode(airportCode);

        if (airportRef == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "airport not found for this code : " + airportCode);
        }

        List<P_BlackoutPeriodRef> p_blackoutPeriodRefs = airportRef.getP_blackoutPeriodRefs();

        return blackoutPeriodMapper.toBeanList(p_blackoutPeriodRefs);
    }

    @Override
    public List<P_BlackoutPeriodRefBean> getBlackoutPeriodRoundTrip(String airportCode1, String airportCode2) {
        List<P_BlackoutPeriodRefBean> blackoutPeriodOneWay = getBlackoutPeriodOneWay(airportCode1);
        blackoutPeriodOneWay.addAll(getBlackoutPeriodOneWay(airportCode2));
        return blackoutPeriodOneWay;
    }

    @Override
    public List<P_BlackoutPeriodRefBean> getAllBlackoutPeriod() {
        return blackoutPeriodMapper.toBeanList(blackoutPeriodRepository.findAll());
    }

    @Override
    public P_BlackoutPeriodRefBean updateBlackoutPeriod(P_BlackoutPeriodRefBean blackoutPeriod, Long id) {

        P_BlackoutPeriodRef blackoutPeriodDB = blackoutPeriodRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Blackout Not found for this Id " + id));

//        P_AirportRef airportRef = getOptionalP_airportRef(blackoutPeriod.getAirportId())
//                .orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND,
//                                "Airport not found for this Id " + blackoutPeriod.getAirportId()));


        if (blackoutPeriod.getStartDate() != null) {

            blackoutPeriodDB.setStartDate(blackoutPeriod.getStartDate());
        }

        if (blackoutPeriod.getEndDate() != null) {
            blackoutPeriodDB.setEndDate(blackoutPeriod.getEndDate());
        }

        boolean dateCheck = isBeforeOrEqual(blackoutPeriodDB.getStartDate(), blackoutPeriodDB.getEndDate());

        if (!dateCheck) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the Start date is greater than end Date");
        }

        if (blackoutPeriod.getReferenceBlackout() != null) {
            blackoutPeriodDB.setReferenceBlackout(blackoutPeriod.getReferenceBlackout());
        }

        return blackoutPeriodMapper.entityToBean(blackoutPeriodRepository.save(blackoutPeriodDB));
    }

    @Override
    public void deleteBlackoutPeriod(Long id) {
        Optional<P_BlackoutPeriodRef> optionalBlackout = blackoutPeriodRepository.findById(id);

        if (!optionalBlackout.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "blackout not found for ID " + id);
        }

        blackoutPeriodRepository.deleteById(id);
    }


    private Optional<P_AirportRef> getOptionalP_airportRef(Long airportId) {
        Optional<P_AirportRef> optionalAirport = airportRepository.findById(airportId);

        if (!optionalAirport.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found for Id " + airportId);
        }
        return optionalAirport;
    }

    private boolean isBeforeOrEqual(LocalDate startDate, LocalDate endDate) {
        return startDate.isBefore(endDate) || startDate.isEqual(endDate);
    }
}
