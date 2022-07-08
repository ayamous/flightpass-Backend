package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.LandingPageConfigBean;
import ma.itroad.ram.flightpass.model.entity.P_CurrencyRef;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;

import java.time.LocalDate;

public interface LPFPDailyOfferService {

    LandingPageConfigBean saveLandingPageConfig(Long configId, P_SegmentRef p_segmentRef, P_CurrencyRef p_currencyRef, LocalDate date);
    LandingPageConfigBean getLandingPageConfig(Long segmentId, String currencyCode);
}
