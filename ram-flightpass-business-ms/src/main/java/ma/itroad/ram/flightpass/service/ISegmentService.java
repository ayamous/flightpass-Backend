


package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.SegmentBean;
import ma.itroad.ram.flightpass.model.bean.SegmentViewBean;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ISegmentService {

    ResponseEntity addSegment(SegmentBean segment);

    Page<SegmentViewBean> getAll(Pageable pageable);

    boolean segmentExist(String airportCode1,String airportCode2);


    P_SegmentRef getSegmentByAirportsCode(String airportCode1,String airportCode2);
}

