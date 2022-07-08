package ma.itroad.ram.flightpass;

import ma.itroad.ram.flightpass.config.FileStorageProperties;
import ma.itroad.ram.flightpass.model.bean.BasePriceHistoryBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxHistoryBean;
import ma.itroad.ram.flightpass.model.bean.SegmentBean;
import ma.itroad.ram.flightpass.model.entity.*;
import ma.itroad.ram.flightpass.repository.*;
import ma.itroad.ram.flightpass.service.*;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@EnableConfigurationProperties({
        FileStorageProperties.class
})

@SpringBootApplication
public class RamFlightpassBusinessMsApplication {


    @Autowired
    private IBasePriceHistoryService iBasePriceHistoryService;

    @Autowired
    private IBaseTaxHistoryService iBaseTaxHistoryService;

    @Autowired
    private ISegmentService iSegmentService;

    @Autowired
    AirportRepository airportRepository;
    @Autowired
    P_FlightPassConfigRepository p_flightPassConfigRepository;

    @Autowired
    P_BaseConfigRefRepository p_baseConfigRefRepository;

    @Autowired
    P_DayToDepartureRefRepository p_dayToDepartureRefRepository;

    @Autowired
    P_PassDelayRefRepository p_passDelayRefRepository;

    @Autowired
    LPFPDailyOfferService lpfpDailyOfferService;

    @Autowired
    EmailService EmailService;

    @Autowired
    UsersResource userService;

    @Bean
    CommandLineRunner commandLineRunner(AirportRepository repository,
                                        CountryRepository countryRepository,
                                        CityRepository cityRepository,
                                        CurrencyRepository currencyRepository,
                                        PriceBaseHistoryRepository priceBaseHistory,
                                        TaxBaseHistoryRepository taxBaseHistory,
                                        SegmentRepository segmentRepository) {


        return args -> {
/*            P_AirportRef first = new P_AirportRef();
            P_AirportRef second = new P_AirportRef();
            airportRepository.save(first);
            airportRepository.save(second);
            P_FlightPassConfig p_flightPassConfig = new P_FlightPassConfig("test" ,first);
            P_FlightPassConfig p_flightPassConfig2 = new P_FlightPassConfig("test2" ,second);
            p_flightPassConfigRepository.save(p_flightPassConfig);
            p_flightPassConfigRepository.save(p_flightPassConfig2);*/


            P_FlightPassConfig p_flightPassConfig = new P_FlightPassConfig();

            Set<P_PassDelayRef> pPassDelayRefs = new HashSet<>();
            pPassDelayRefs.add(new P_PassDelayRef(1, 0.15));
            pPassDelayRefs.add(new P_PassDelayRef(2, 0.10));
            pPassDelayRefs.add(new P_PassDelayRef(3, 0.05));
            pPassDelayRefs.add(new P_PassDelayRef(4, 0.15));
            pPassDelayRefs.add(new P_PassDelayRef(5, 0.10));
            pPassDelayRefs.add(new P_PassDelayRef(6, 0.05));
            pPassDelayRefs.add(new P_PassDelayRef(7, 0.05));
            pPassDelayRefs.add(new P_PassDelayRef(8, 0.05));
            pPassDelayRefs.add(new P_PassDelayRef(9, 0.05));
            pPassDelayRefs.add(new P_PassDelayRef(10, 0.05));
            p_passDelayRefRepository.saveAll(pPassDelayRefs);

            Set<P_BaseConfigRef> p_baseConfigRefs = new HashSet<>();
            p_baseConfigRefs.add(new P_BaseConfigRef(10, 0.08));
            p_baseConfigRefs.add(new P_BaseConfigRef(15, 0.05));
            p_baseConfigRefs.add(new P_BaseConfigRef(20, 0.03));
            p_baseConfigRefRepository.saveAll(p_baseConfigRefs);


            Set<P_DayToDepartureRef> p_dayToDepartureRefs = new HashSet<>();
            p_dayToDepartureRefs.add(new P_DayToDepartureRef(10, 0.03));
            p_dayToDepartureRefs.add(new P_DayToDepartureRef(20, 0.05));
            p_dayToDepartureRefs.add(new P_DayToDepartureRef(30, 0.10));
            p_dayToDepartureRefRepository.saveAll(p_dayToDepartureRefs);

            p_flightPassConfig.setP_baseConfigRefs(p_baseConfigRefs);
            p_flightPassConfig.setP_passDelayRefs(pPassDelayRefs);
            p_flightPassConfig.setP_dayToDepartureRefs(p_dayToDepartureRefs);

            p_flightPassConfigRepository.save(p_flightPassConfig);

            Stream.of(
                    new P_AirportRef("Mohammed V International Airport", "CMN",
                            "33.3675003", "-7.5899701", new P_CityRef("Casablanca", new P_CountryRef("Morocco", "MA"))),
                    new P_AirportRef("Leonardo Da Vinci–Fiumicino Airport", "FCO",
                            "41.8044444", "12.2508333",
                            new P_CityRef("Rome",
                                    new P_CountryRef("Italy", "IT"))),
                    new P_AirportRef("Charles De Gaulle International Airport", "CDG",
                            "49.0127983", "2.55",
                            new P_CityRef("Paris",
                                    new P_CountryRef("France", "FR"))),

                    new P_AirportRef("Barcelona International Airport", "BCN",
                            "41.2971001", "2.07846",
                            new P_CityRef("Barcelona", new P_CountryRef("Spain", "ES"))),
                    new P_AirportRef("Blaise Diagne International Airport", "DSS",
                            "14.671111", "-17.066944",
                            new P_CityRef("Dakar", new P_CountryRef("Senegal", "SN"))),
                    new P_AirportRef("John F Kennedy International Airport", "JFK",
                            "40.639801", "-73.7789002",
                            new P_CityRef("New York", new P_CountryRef("United States", "US")))


            ).forEach(repository::save);

            P_AirportRef ory = new P_AirportRef("Paris-Orly Airport", "ORY",
                    "48.7252998", "2.3594401", null);
            repository.save(ory);
            ory.setCity(cityRepository.findByName("Paris"));
            repository.save(ory);

            iSegmentService.addSegment(new SegmentBean(1L, 2L));
            iSegmentService.addSegment(new SegmentBean(1L, 3L));
            iSegmentService.addSegment(new SegmentBean(1L, 4L));
            iSegmentService.addSegment(new SegmentBean(1L, 5L));
            iSegmentService.addSegment(new SegmentBean(1L, 6L));
//            iSegmentService.addSegment(new SegmentBean(1L, 7L));

            Stream.of(
                    new P_CurrencyRef("EUR", "Euro"),
                    new P_CurrencyRef("MAD", "Moroccan dirham"),
                    new P_CurrencyRef("USD", "United States dollar")
            ).forEach(currencyRepository::save);

            segmentRepository.findAll().forEach(p_segmentRef -> {
                p_segmentRef.setP_flightPassConfig(p_flightPassConfig);
                segmentRepository.save(p_segmentRef);
            });

            segmentRepository.findAll().forEach(p_segmentRef -> {
//                p_segmentRef.setP_flightPassConfig(p_flightPassConfig);
//                segmentRepository.save(p_segmentRef);

                String code1 = p_segmentRef.getArrivalAirportRef().getCode();
                String code2 = p_segmentRef.getDepartureAirportRef().getCode();
                LocalDate localDate = LocalDate.now();

                currencyRepository.findAll().forEach(currency -> {

                            BasePriceHistoryBean bean1 = new BasePriceHistoryBean(code1, code2,
                                    getRandom(100, 3000), localDate,
                                    currency.getCode());

                            iBasePriceHistoryService.addBasePriceHistory(bean1);

                            Stream.of(code1, code2).forEach(flightTo -> {
                                BaseTaxHistoryBean bean2 = new BaseTaxHistoryBean(code1, code2,
                                        getRandom(0, 1000), localDate, currency.getCode(), flightTo);
                                iBaseTaxHistoryService.addTaxBaseHistory(bean2);
                            });

                            lpfpDailyOfferService.saveLandingPageConfig(p_segmentRef.getP_flightPassConfig().getId(),
                                    p_segmentRef, currency, localDate);
                        }
                );

                segmentRepository.save(p_segmentRef);
            });


//            EmailService.send("elaouniredouane@gmail.com",buildEmail("Redouane EL AOUni"), );
//
//            P_AirportRef first = new P_AirportRef();
//            P_AirportRef second = new P_AirportRef();
//            P_FlightPassConfig p_flightPassConfig = new P_FlightPassConfig();
//            P_FlightPassConfig p_flightPassConfig2 = new P_FlightPassConfig();
//            p_flightPassConfigRepository.save(p_flightPassConfig2);
//
//
//            segmentRepository.save(new P_SegmentRef(
//                    airportRepository.save(first),
//                    airportRepository.save(second),
//                    p_flightPassConfigRepository.save(p_flightPassConfig)
//            ));


            userService.list().forEach(userRepresentation -> {
                System.out.print(userRepresentation.getEmail() + " ");
                System.out.print(userRepresentation.getFirstName() + " ");
                System.out.print(userRepresentation.getLastName() + " ");
                System.out.print(userRepresentation.getCredentials() + " ");
            });

        };
    }

    private double getRandom(double a, double b) {
        double random = (ThreadLocalRandom.current().nextDouble(a, b));
        random = Math.round(random * 100);
        random /= 100;
        return random;
    }

    static LocalDate randomDate() {
        long minDay = LocalDate.now().toEpochDay();
        long maxDay = LocalDate.of(2024, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        return randomDate;
    }

    public static void main(String[] args) {
        SpringApplication.run(RamFlightpassBusinessMsApplication.class, args);
    }


}






/*
    @Bean
    CommandLineRunner start(SegmentRepository segmentRepository){
        return args -> {
            P_AirportRef first = new P_AirportRef();
            P_AirportRef second = new P_AirportRef();
            P_FlightPassConfig p_flightPassConfig = new P_FlightPassConfig();
            P_FlightPassConfig p_flightPassConfig2 = new P_FlightPassConfig();
            p_flightPassConfigRepository.save(p_flightPassConfig2);


            segmentRepository.save(new P_SegmentRef(
                    airportRepository.save(first),
                    airportRepository.save(second),
                    p_flightPassConfigRepository.save(p_flightPassConfig)
            ));
        };
    }*/
