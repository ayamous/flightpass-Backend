package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.FlightPassOverviewBean;
import ma.itroad.ram.flightpass.model.entity.D_FlightPassCustomerOrder;
import ma.itroad.ram.flightpass.model.enums.CouponStatus;
import ma.itroad.ram.flightpass.repository.D_Pass2FlightTicketRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",imports = CouponStatus.class)
public abstract class FlightPassOverviewMapper {

    @Autowired
    protected D_Pass2FlightTicketRepository ticketRepository;


    @Mapping(target = "orderId",source = "id")
    @Mapping(target = "consumedCoupons", expression = "java(ticketRepository.countByFpOrderAndCouponStatus(order,CouponStatus.ISSUED))")
    @Mapping(target = "departureAirport",source = "flySegmentRef.departureAirportRef.name")
    @Mapping(target = "departureCity",source = "flySegmentRef.departureAirportRef.city.name")
    @Mapping(target = "departureAirportCode",source = "flySegmentRef.departureAirportRef.code")
    @Mapping(target = "arrivalAirport",source = "flySegmentRef.arrivalAirportRef.name")
    @Mapping(target = "arrivalCity",source = "flySegmentRef.arrivalAirportRef.city.name")
    @Mapping(target = "arrivalAirportCode",source = "flySegmentRef.arrivalAirportRef.code")
    @Mapping(target = "dayToTravel",source = "fpDTD")
    @Mapping(target = "expiryDate",source = "fpExpiryDate")
    @Mapping(target = "orderDate",source = "fpOrderDate")
    @Mapping(target = "price",source = "fpCalculatedFinalPrice")
    @Mapping( target="remainingCoupons", expression="java( order.getFpPassNumber() - overview.getConsumedCoupons())" )
    @Mapping(expression = "java(order.getFpPassengerRef().getFirstName()+ \" \" +order.getFpPassengerRef().getLastName())", target = "fullNamePassenger")
    public abstract FlightPassOverviewBean toBean(D_FlightPassCustomerOrder order, @MappingTarget FlightPassOverviewBean overview);
}
