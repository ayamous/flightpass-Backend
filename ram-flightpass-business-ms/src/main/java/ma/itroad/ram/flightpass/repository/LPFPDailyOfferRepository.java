package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.G_LPFPDailyOffer;
import ma.itroad.ram.flightpass.model.entity.P_CurrencyRef;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface LPFPDailyOfferRepository extends JpaRepository<G_LPFPDailyOffer,Long> {

    G_LPFPDailyOffer findByFlySegmentRefAndFpCurrencyRefAndFpDailyOfferDate(P_SegmentRef p_segmentRef,
                                                                            P_CurrencyRef p_currencyRef,
                                                                            LocalDate date);
}
