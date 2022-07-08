package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.FPCustomerOrderBean;
import ma.itroad.ram.flightpass.model.bean.PassengerBean;
import ma.itroad.ram.flightpass.model.entity.D_FlightPassCustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FPCustomerOrderMapper {

    PassengerBean toPassengerBean(FPCustomerOrderBean fpCustomerOrderBean);

    @Mapping(target = "currency",source = "fpCurrencyRef.code")
    @Mapping(target = "baseConfig",source = "fpPassNumber")
    @Mapping(target = "dayToTravel",source = "fpDTD")
    @Mapping(target = "passDelay",source = "fpPeriod")
    @Mapping(target = "passenger",source = "fpPassengerRef")
    @Mapping(target = "configuredAs",source = "createdBy")
    @Mapping(target = "airportDeparture",source = "flySegmentRef.departureAirportRef.name")
    @Mapping(target = "cityDeparture",source = "flySegmentRef.departureAirportRef.city.name")
    @Mapping(target = "airportArrival",source = "flySegmentRef.arrivalAirportRef.name")
    @Mapping(target = "cityArrival",source = "flySegmentRef.arrivalAirportRef.city.name")
    FPCustomerOrderBean toBean(D_FlightPassCustomerOrder order);

//    @AfterMapping
//    default void setSegment(@MappingTarget FPCustomerOrderBean orderBean,
//                            D_FlightPassCustomerOrder order){
//        P_SegmentRef flySegmentRef = order.getFlySegmentRef();
//
//        orderBean.setSegment( flySegmentRef.getDepartureAirportRef().getCode()+ " - "+flySegmentRef.getArrivalAirportRef().getCode());
//    }
}
