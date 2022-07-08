package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.BaseTaxHistoryBean;
import ma.itroad.ram.flightpass.model.entity.D_TaxBaseHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseTaxMapper {

    @Mapping(source = "segment.departureAirportRef.code",  target = "departureAirportCode")
    @Mapping(source = "segment.arrivalAirportRef.code",  target = "arrivalAirportCode")
    @Mapping(source = "currencyRef.code",  target = "currencyCode")
    @Mapping(source = "flightFrom.code",  target = "flightFrom")
    BaseTaxHistoryBean entityToBean(D_TaxBaseHistory taxBaseHistory);

    @Mapping(source = "segment.departureAirportRef.code",  target = "departureAirportCode")
    @Mapping(source = "segment.arrivalAirportRef.code",  target = "arrivalAirportCode")
    @Mapping(source = "currencyRef.code",  target = "currencyCode")
    @Mapping(source = "flightFrom.code",  target = "flightFrom")
    List<BaseTaxHistoryBean> entityToBeanList(List<D_TaxBaseHistory> taxBaseHistory);
}
