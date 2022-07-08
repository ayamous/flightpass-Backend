package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.*;
import ma.itroad.ram.flightpass.model.entity.G_LPFPDailyOffer;
import ma.itroad.ram.flightpass.model.entity.P_CurrencyRef;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import ma.itroad.ram.flightpass.repository.CurrencyRepository;
import ma.itroad.ram.flightpass.repository.LPFPDailyOfferRepository;
import ma.itroad.ram.flightpass.repository.SegmentRepository;
import ma.itroad.ram.flightpass.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LPFPDailyOfferServiceImpl implements LPFPDailyOfferService {


    private P_DayToDepartureRefService dayToDepartureService;
    private P_PassDelayRefService passDelayService;
    private P_BaseConfigRefService baseConfigService;
    private LPFPDailyOfferRepository dailyOfferRepository;
    private FlightPassService flightPassService;
    private SegmentRepository segmentRepository;
    private CurrencyRepository currencyRepository;
    private P_BaseConfigRefService baseConfigRefService;
    private P_DayToDepartureRefService dayToDepartureRefService;
    private P_PassDelayRefService passDelayRefService;
    @Override
    public LandingPageConfigBean saveLandingPageConfig(Long configId, P_SegmentRef p_segmentRef, P_CurrencyRef p_currencyRef, LocalDate date) {
        P_BaseConfigRefBean minBaseConfig = baseConfigService.minBaseConfig(configId);

        P_PassDelayRefBean minPassDelay = passDelayService.getMinPassDelay(configId);

        P_DayToDepartureRefBean maxDayToDeparture = dayToDepartureService.getMaxDayToDeparture(configId);

        FlightPassPriceRequestBean passPriceBean = new FlightPassPriceRequestBean(
                p_segmentRef.getId(), p_currencyRef.getCode(), date, minBaseConfig.getNbrFlights(),
                 maxDayToDeparture.getNbrDays(), minPassDelay.getNbrMonths()
        );


        FlightPassBean flightPassBean = flightPassService.calculateFlightPassPrice(passPriceBean);


        G_LPFPDailyOffer dailyOffer = dailyOfferRepository.save(new G_LPFPDailyOffer(date, p_currencyRef, p_segmentRef,
                flightPassBean.getFlightpassPrice(),
                flightPassBean.getBasePrice(),
                flightPassBean.getBaseTaxe(),
                minBaseConfig.getNbrFlights(),
                maxDayToDeparture.getNbrDays(),
                minPassDelay.getNbrMonths()));



        return new LandingPageConfigBean(
                minBaseConfig,maxDayToDeparture,minPassDelay
                ,flightPassBean.getSegment(), flightPassBean.getCurrency(),
                flightPassBean.getFlightpassPrice()
        );

    }

    @Override
    public LandingPageConfigBean getLandingPageConfig(Long segmentId, String currencyCode) {

        P_CurrencyRef p_currencyRef = currencyRepository.findByCode(currencyCode);
        Optional<P_SegmentRef> segmentRefOptional = segmentRepository.findById(segmentId);

        if (!segmentRefOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "segment not found");
        }

        if (p_currencyRef == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "currency not found");
        }

        P_SegmentRef p_segmentRef = segmentRefOptional.get();



        G_LPFPDailyOffer dailyOffer = dailyOfferRepository.
                findByFlySegmentRefAndFpCurrencyRefAndFpDailyOfferDate
                        (p_segmentRef, p_currencyRef, LocalDate.now());

        if(dailyOffer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"no daily config offer found");
        }

        String segment = p_segmentRef.getDepartureAirportRef().getCode() + " - "+p_segmentRef.getArrivalAirportRef().getCode();

        Long configId = dailyOffer.getFlySegmentRef().getP_flightPassConfig().getId();

        P_BaseConfigRefBean baseConfigRefBean = baseConfigRefService.
                getBaseConfigRefByNbrFlightsAndConfigId(configId, dailyOffer.getFpPassNumberBase());

        P_DayToDepartureRefBean dayToDepartureRefBean = dayToDepartureRefService.
                getDayToDepartureRefByNbrDaysAndConfigId(configId, dailyOffer.getFpDateToTravelBase());


        P_PassDelayRefBean passDelayRefBean = passDelayRefService.
                getPassDelayRefByNbrMonthsAndConfigId(configId, dailyOffer.getFpPeriodBase());

        return new LandingPageConfigBean(
baseConfigRefBean,dayToDepartureRefBean,passDelayRefBean,segment,dailyOffer.getFpCurrencyRef().getCode(),
                dailyOffer.getFpCalculatedBeginningPrice()
        );
    }
}
