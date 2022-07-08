
package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.D_PriceBaseHistory;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SegmentRepository extends JpaRepository<P_SegmentRef, Long> {

    P_SegmentRef findByDepartureAirportRefCodeAndArrivalAirportRefCode(String departureCode, String ArrivalCode);

    P_SegmentRef findByDepartureAirportRefIdAndArrivalAirportRefId(Long id1, Long id2);


}

