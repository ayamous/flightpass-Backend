
package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaxBaseHistoryRepository extends JpaRepository<D_TaxBaseHistory, Long> {

    D_TaxBaseHistory findByDateAndCurrencyRefAndSegment(LocalDate date, P_CurrencyRef currencyRef, P_SegmentRef segmentRef);

    List<D_TaxBaseHistory> findByCurrencyRefCodeAndDateAndSegmentId(String codeCurrency,LocalDate date,Long segmentId);

    Page<D_TaxBaseHistory> findBySegmentId(Long id, Pageable pageable);

    D_TaxBaseHistory findBySegmentAndDateAndCurrencyRefAndFlightFrom(P_SegmentRef segmentRef, LocalDate date, P_CurrencyRef currencyRef, P_AirportRef p_airportRef);
}

