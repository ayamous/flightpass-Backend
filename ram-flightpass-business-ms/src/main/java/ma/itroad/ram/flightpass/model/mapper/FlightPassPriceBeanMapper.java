package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.BasePriceRequestBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxRequestBean;
import ma.itroad.ram.flightpass.model.bean.FlightPassPriceRequestBean;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightPassPriceBeanMapper {

    BaseTaxRequestBean toTaxRequestBean(FlightPassPriceRequestBean passPriceBean);
    BasePriceRequestBean toPriceRequestBean(FlightPassPriceRequestBean passPriceBean);
}
