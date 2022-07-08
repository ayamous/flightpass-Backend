package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.BasePriceHistoryBean;
import ma.itroad.ram.flightpass.model.entity.D_PriceBaseHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BasePriceMapper {

    @Mapping(source = "segment.departureAirportRef.code",  target = "departureAirportCode")
    @Mapping(source = "segment.arrivalAirportRef.code",  target = "arrivalAirportCode")
    @Mapping(source = "currencyRef.code",  target = "currencyCode")
    BasePriceHistoryBean entityToBean(D_PriceBaseHistory priceBaseHistory);
}
