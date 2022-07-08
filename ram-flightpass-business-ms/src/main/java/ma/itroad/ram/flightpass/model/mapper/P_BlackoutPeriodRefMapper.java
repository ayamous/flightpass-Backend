package ma.itroad.ram.flightpass.model.mapper;


import ma.itroad.core.model.mapper.GenericModelMapper;
import ma.itroad.ram.flightpass.model.bean.P_BlackoutPeriodRefBean;
import ma.itroad.ram.flightpass.model.entity.P_BlackoutPeriodRef;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface P_BlackoutPeriodRefMapper extends GenericModelMapper<P_BlackoutPeriodRef, P_BlackoutPeriodRefBean> {
    @Override
    @Mapping(target = "airportId",source = "arrivalAirport.id")
     P_BlackoutPeriodRefBean entityToBean(P_BlackoutPeriodRef entity);
}
