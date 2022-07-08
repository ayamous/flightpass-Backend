

package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.SegmentBean;
import ma.itroad.ram.flightpass.model.bean.SegmentViewBean;
import ma.itroad.ram.flightpass.model.entity.*;
import ma.itroad.ram.flightpass.model.mapper.SegmentMapper;
import ma.itroad.ram.flightpass.repository.*;
import ma.itroad.ram.flightpass.service.ISegmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class SegmentServiceImpl implements ISegmentService {

    private AirportRepository airportRepository;
    private PriceBaseHistoryRepository priceBaseHistoryRepository;
    private SegmentRepository segmentRepository;
    private CurrencyRepository currencyRepository;
    private TaxBaseHistoryRepository taxBaseHistoryRepository;
    private SegmentMapper segmentMapper;


    @Override
    public ResponseEntity<?> addSegment(SegmentBean segment) {

        P_AirportRef departureAirport = airportRepository.findById(segment.getDepartureAirportID()).get();
        P_AirportRef arrivalAirport = airportRepository.findById(segment.getArrivalAirportID()).get();

        String departureAirportCode = departureAirport.getCode();
        String arrivalAirportCode = arrivalAirport.getCode();

        if (segmentExist(departureAirportCode,arrivalAirportCode)) {
            return new ResponseEntity<>("Segment already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }



        P_SegmentRef segmentRef = new P_SegmentRef();
        segmentRef.setDepartureAirportRef(departureAirport);
        segmentRef.setArrivalAirportRef(arrivalAirport);
        segmentRepository.save(segmentRef);


        return new ResponseEntity<>(segment, HttpStatus.CREATED);
    }

    @Override
    public Page<SegmentViewBean> getAll(Pageable pageable) {
        return segmentRepository.findAll(pageable).map(segmentMapper::entityToBean);
    }



    @Override
    public boolean segmentExist(String airportCode1, String airportCode2) {
        return segmentRepository.
                findByDepartureAirportRefCodeAndArrivalAirportRefCode(airportCode1,airportCode2) != null
                ||segmentRepository.findByDepartureAirportRefCodeAndArrivalAirportRefCode(airportCode2,airportCode1) != null;
    }



    @Override
    public P_SegmentRef getSegmentByAirportsCode(String airportCode1, String airportCode2) {

        return segmentRepository.findByDepartureAirportRefCodeAndArrivalAirportRefCode(airportCode1,airportCode2) ;
    }
}


