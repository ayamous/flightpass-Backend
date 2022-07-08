package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.Pass2FlightTicketBean;
import ma.itroad.ram.flightpass.model.entity.D_Pass2FlightTicket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface Pass2FlightTicketMapper {

    @Mapping(target = "reservationDate",source = "flightReservation.reservationDate")
    @Mapping(target = "reservationId",source = "flightReservation.reservationID")
    @Mapping(target = "pnrPass",source = "pnrPass")
    @Mapping(target = "flightNumber",source = "flightReservation.flightNumber")
    @Mapping(target = "departureAirport",source = "flightReservation.departureAirportRef.code")
    @Mapping(target = "arrivalAirport",source = "flightReservation.arrivalAirportRef.code")
    @Mapping(target = "departureDateTime",source = "flightReservation.departureDateTime")
    @Mapping(target = "arrivalDateTime",source = "flightReservation.arrivalDateTime")
    @Mapping(target = "seatNumber",source = "flightReservation.seatNumber")
    @Mapping(target = "maxKg",source = "flightReservation.maxKg")
    @Mapping(target = "mobileNumberReservation",source = "flightReservation.mobileNumberReservation")
    @Mapping(target = "emailReservation",source = "flightReservation.emailReservation")
    Pass2FlightTicketBean toBean(D_Pass2FlightTicket entity);
}
