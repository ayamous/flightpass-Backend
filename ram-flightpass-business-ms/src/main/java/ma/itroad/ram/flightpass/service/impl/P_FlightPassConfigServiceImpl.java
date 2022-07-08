package ma.itroad.ram.flightpass.service.impl;


import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.exception.AirPortNotFoundException;
import ma.itroad.ram.flightpass.exception.ConfiguratioNotFound;
import ma.itroad.ram.flightpass.exception.PostConfiguratiotNotAcceptable;
import ma.itroad.ram.flightpass.model.bean.*;
import ma.itroad.ram.flightpass.model.entity.P_FlightPassConfig;
import ma.itroad.ram.flightpass.model.mapper.P_FlightPassConfigMapper;
import ma.itroad.ram.flightpass.repository.AirportRepository;
import ma.itroad.ram.flightpass.repository.P_FlightPassConfigRepository;
import ma.itroad.ram.flightpass.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class P_FlightPassConfigServiceImpl implements P_FlightPassConfigService {

    private P_FlightPassConfigRepository p_flightPassConfigRepository;
    private P_FlightPassConfigMapper pFlightPassConfigMapper;
    private AirportRepository airportRepository;

    private P_DayToDepartureRefService dayToDepartureService;
    private P_PassDelayRefService passDelayService;
    private P_BaseConfigRefService baseConfigService;

    private FlightPassService flightPassService;




    @Override
    public P_FlightPassConfigBean postFlightPassConfig(String reference ,Long localAirportRef_Id ) {
        if (!airportRepository.findById(localAirportRef_Id).isPresent()){
            throw new AirPortNotFoundException("Airport does not exist ");
        }
        if (p_flightPassConfigRepository.findByReference(reference)!=null){
            throw new PostConfiguratiotNotAcceptable("a config with this reference already exists");
        }
        P_FlightPassConfig p_flightPassConfig =p_flightPassConfigRepository.save(
                new P_FlightPassConfig(reference ,airportRepository.getById(localAirportRef_Id))
        );
        P_FlightPassConfigBean p_flightPassConfigBean =pFlightPassConfigMapper.p_FlightPassConfigEntityToBean(p_flightPassConfig);
        p_flightPassConfigBean.setP_AirportRef_id(
                p_flightPassConfig.getP_airportRef().getId()
        );
        return p_flightPassConfigBean;
    }

    @Override
    public List<P_FlightPassConfigBean> getFlightPassConfigs() {
            return p_flightPassConfigRepository.findAll().stream().map(flightPassConfigs -> {
                P_FlightPassConfigBean p_flightPassConfigBean = pFlightPassConfigMapper.p_FlightPassConfigEntityToBean(flightPassConfigs);
                p_flightPassConfigBean.setP_AirportRef_id(flightPassConfigs.getP_airportRef().getId());
                return p_flightPassConfigBean;
            }).collect(Collectors.toList());
    }

    @Override
    public P_FlightPassConfigBean getFlightPassConfigById(Long configId) {
        if (!p_flightPassConfigRepository.findById(configId).isPresent()){
            return null;
        }
        P_FlightPassConfig p_flightPassConfig =p_flightPassConfigRepository.getById(configId);
        P_FlightPassConfigBean p_flightPassConfigBean = pFlightPassConfigMapper.p_FlightPassConfigEntityToBean(p_flightPassConfig);
        p_flightPassConfigBean.setP_AirportRef_id(p_flightPassConfig.getP_airportRef().getId());
        return p_flightPassConfigBean;


    }

    @Override
    public P_FlightPassConfigBean getFlightPassConfigByReference(String reference) {
        if (p_flightPassConfigRepository.findByReference(reference)==null){
            return null;
        }
        P_FlightPassConfig p_flightPassConfig =p_flightPassConfigRepository.findByReference(reference);
        P_FlightPassConfigBean p_flightPassConfigBean = pFlightPassConfigMapper.p_FlightPassConfigEntityToBean(p_flightPassConfig);
        p_flightPassConfigBean.setP_AirportRef_id(p_flightPassConfig.getP_airportRef().getId());
        return p_flightPassConfigBean;
    }


    @Override
    public P_FlightPassConfigBean patchFlightPassConfig(Long configId, Boolean enabled) {
        if (!p_flightPassConfigRepository.findById(configId).isPresent()){
            throw new ConfiguratioNotFound("a configuration with this id  does not exist ");
        }
        P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
        p_flightPassConfig.setEnabled(enabled);
        P_FlightPassConfigBean p_flightPassConfigBean =pFlightPassConfigMapper.p_FlightPassConfigEntityToBean(
                p_flightPassConfigRepository.save(p_flightPassConfig)
        );
        p_flightPassConfigBean.setP_AirportRef_id(p_flightPassConfig.getP_airportRef().getId());
        return p_flightPassConfigBean;

    }

    @Override
    public LandingPageConfigBean getDefaultConfig(Long configId, Long segmentId, String currency) {

        P_BaseConfigRefBean minBaseConfig = baseConfigService.minBaseConfig(configId);
        P_BaseConfigRefBean maxBaseConfig = baseConfigService.maxBaseConfig(configId);

        P_PassDelayRefBean maxPassDelay = passDelayService.getMaxPassDelay(configId);
        P_PassDelayRefBean minPassDelay = passDelayService.getMinPassDelay(configId);

        P_DayToDepartureRefBean minDayToDeparture = dayToDepartureService.getMinDayToDeparture(configId);
        P_DayToDepartureRefBean maxDayToDeparture = dayToDepartureService.getMaxDayToDeparture(configId);

//        FlightPassPriceRequestBean passPriceBean = new FlightPassPriceRequestBean(
//                segmentId,currency, LocalDate.now(),minBaseConfig.getNbrFlights(),
//                minBaseConfig.getPercentage(),maxDayToDeparture.getPercentage(),minPassDelay.getPercentage()
//        );

//        double flightPassPrice = flightPassService.calculateFlightPassPrice(passPriceBean).getFlightpassPrice();

//        return new LandingPageConfigBean(
//                maxBaseConfig,
//                minDayToDeparture,
//                maxPassDelay,
//                flightPassPrice
//        );

        return null;

    }
}
