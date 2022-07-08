//package ma.itroad.ram.flightpass.model.mapper;
//
//import ma.itroad.ram.flightpass.model.bean.LandingPageConfigBean;
//import ma.itroad.ram.flightpass.model.entity.G_LPFPDailyOffer;
//import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
//import org.mapstruct.AfterMapping;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//
//@Mapper(componentModel = "spring")
//public interface LPFPDailyOfferMapper {
//
//    @Mapping(source = "fpCalculatedBeginningPrice",  target = "flightpassPrice")
//    @Mapping(source = "fpPassNumberBase",  target = "baseConfig")
//    @Mapping(source = "fpDateToTravelBase",  target = "dayToDeparture")
//    @Mapping(source = "fpPeriodBase",  target = "passDelay")
//    @Mapping(source = "fpCurrencyRef.code",  target = "currency")
//    LandingPageConfigBean toBean(G_LPFPDailyOffer g_lpfpDailyOffer);
//
//    @AfterMapping
//    default void setSegment(@MappingTarget LandingPageConfigBean segment,G_LPFPDailyOffer dailyOffer){
//        P_SegmentRef flySegmentRef = dailyOffer.getFlySegmentRef();
//
//        segment.setSegment( flySegmentRef.getDepartureAirportRef().getCode()+ " - "+flySegmentRef.getArrivalAirportRef().getCode());
//    }
//
//}