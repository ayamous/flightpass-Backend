//package ma.itroad.ram.flightpass.service.impl;
//
//import lombok.AllArgsConstructor;
//import ma.itroad.ram.flightpass.model.bean.AirportBean;
//import ma.itroad.ram.flightpass.model.entity.P_AirportRef;
//import ma.itroad.ram.flightpass.model.entity.P_CityRef;
//import ma.itroad.ram.flightpass.model.entity.P_CountryRef;
//import ma.itroad.ram.flightpass.model.mapper.AirportMapper;
//import ma.itroad.ram.flightpass.repository.AirportRepository;
//import ma.itroad.ram.flightpass.repository.CityRepository;
//import ma.itroad.ram.flightpass.repository.CountryRepository;
//import ma.itroad.ram.flightpass.service.IAirportService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//@AllArgsConstructor
//public class AirportServiceImpl implements IAirportService {
//
//    private AirportRepository airportRepository;
//    private CityRepository cityRepository;
//    private CountryRepository countryRepository;
//    private AirportMapper airportMapper;
//
//    @Override
//    public AirportBean addAirport(AirportBean airportBean) {
//
//        P_CityRef cityRef = cityRepository.findByName(airportBean.getCityName());
//        P_CountryRef countryRef = countryRepository.findByName(airportBean.getCountryName());
//
//        if (countryRef == null) {
//            countryRef = new P_CountryRef(airportBean.getCountryName());
//            cityRepository.save(new P_CityRef(airportBean.getCityName(), null, countryRef));
//            countryRepository.save(countryRef);
//        }
//
//        if (cityRef == null) {
//
//        }
//
//        P_AirportRef airportRef = airportMapper.beanToEntity(airportBean);
//        airportRef.setCity(cityRef);
//        airportRepository.save(airportRef);
//
//        return airportMapper.entityToBean(airportRef);
//    }
//}
