package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.SegmentViewBean;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SegmentMapper {

    @Mapping(source = "id",target="segmentId")
    @Mapping(source = "p_flightPassConfig.id",target="configId")
    @Mapping(source = "departureAirportRef.city.name",target = "departureAirportRef.city")
    @Mapping(source = "arrivalAirportRef.city.name",target = "arrivalAirportRef.city")
    @Mapping(source = "departureAirportRef.city.country.name",target = "departureAirportRef.country")
    @Mapping(source = "arrivalAirportRef.city.country.name",target = "arrivalAirportRef.country")
    @Mapping(source = "departureAirportRef.city.country.isoCode",target = "departureAirportRef.isoCode")
    @Mapping(source = "arrivalAirportRef.city.country.isoCode",target = "arrivalAirportRef.isoCode")
    SegmentViewBean entityToBean(P_SegmentRef segmentRef);
}
