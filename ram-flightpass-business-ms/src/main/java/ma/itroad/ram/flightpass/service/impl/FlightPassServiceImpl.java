package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.*;
import ma.itroad.ram.flightpass.model.entity.*;
import ma.itroad.ram.flightpass.model.mapper.FlightPassPriceBeanMapper;
import ma.itroad.ram.flightpass.repository.*;
import ma.itroad.ram.flightpass.service.FlightPassService;
import ma.itroad.ram.flightpass.service.IBasePriceHistoryService;
import ma.itroad.ram.flightpass.service.IBaseTaxHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class FlightPassServiceImpl implements FlightPassService {

    private IBasePriceHistoryService iBasePriceHistoryService;
    private IBaseTaxHistoryService iBaseTaxHistoryService;
    private SegmentRepository segmentRepository;
    private P_BaseConfigRefRepository baseConfigRefRepository;
    private P_DayToDepartureRefRepository dayToDepartureRefRepository;
    private P_FlightPassConfigRepository passConfigRepository;
    private P_PassDelayRefRepository passDelayRefRepository;
    @Autowired
    private FlightPassPriceBeanMapper flightPassMapper;


    @Override
    public FlightPassBean calculateFlightPassPrice(FlightPassPriceRequestBean passPriceBean) {

        BasePriceRequestBean basePriceRequestBean = flightPassMapper.toPriceRequestBean(passPriceBean);
        BaseTaxRequestBean baseTaxRequestBean = flightPassMapper.toTaxRequestBean(passPriceBean);

        P_SegmentRef p_segmentRef = segmentRepository.findById(passPriceBean.getSegmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"segment not found"));

        P_FlightPassConfig p_flightPassConfig = p_segmentRef.getP_flightPassConfig();

        P_BaseConfigRef p_baseConfigRef = baseConfigRefRepository.
                findByNbrFlightsAndFlightPassConfigs_Id(passPriceBean.getNbrFlights(), p_flightPassConfig.getId());

        if(p_baseConfigRef == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the configuration does not exist for "+passPriceBean.getNbrFlights()+" flights");
        }

        P_PassDelayRef delayRef = passDelayRefRepository.
                findByNbrMonthsAndFlightPassConfigs_Id(passPriceBean.getPassDelay(),p_flightPassConfig.getId() );

        if(delayRef == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the configuration does not exist for "+passPriceBean.getPassDelay()+" months of pass validity");
        }

        P_DayToDepartureRef departureRef = dayToDepartureRefRepository.
                findByNbrDaysAndFlightPassConfigs_Id(passPriceBean.getDayToTravel(), p_flightPassConfig.getId());

        if(departureRef == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the configuration does not exist for "+passPriceBean.getDayToTravel()+" days of departure");
        }

        double basePrice = iBasePriceHistoryService.getBasePriceBySegmentAndCurrency(basePriceRequestBean).getAmount();
        List<BaseTaxHistoryBean> taxes = iBaseTaxHistoryService.getTaxesBySegmentAndCurrencyAndDate(baseTaxRequestBean);
        double avgTaxes = taxes
                .stream().mapToDouble(BaseTaxHistoryBean::getAmount).average().getAsDouble();

        double basePriceCalculated = basePrice - basePrice * (p_baseConfigRef.getPercentage() + delayRef.getPercentage() + departureRef.getPercentage());

        double flightPassPrice = (basePriceCalculated + avgTaxes )*passPriceBean.getNbrFlights();

        String segment = taxes.get(0).getDepartureAirportCode()+ " - "+taxes.get(0).getArrivalAirportCode();
        String currency = taxes.get(0).getCurrencyCode();
        LocalDate date = taxes.get(0).getDate();


        FlightPassBean flightPassBean = new FlightPassBean(segment,currency, basePrice, avgTaxes, flightPassPrice, date);

        return flightPassBean;
    }


}
