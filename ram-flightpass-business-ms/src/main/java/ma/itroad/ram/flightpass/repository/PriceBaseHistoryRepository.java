
package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.D_PriceBaseHistory;
import ma.itroad.ram.flightpass.model.entity.P_CurrencyRef;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface PriceBaseHistoryRepository extends JpaRepository<D_PriceBaseHistory, Long> {


    D_PriceBaseHistory findByDateAndCurrencyRefAndSegment(LocalDate date, P_CurrencyRef currencyRef, P_SegmentRef segmentRef);

    D_PriceBaseHistory findByDate(LocalDate date);

   Page<D_PriceBaseHistory> findBySegmentId(Long id,Pageable pageable);




}

