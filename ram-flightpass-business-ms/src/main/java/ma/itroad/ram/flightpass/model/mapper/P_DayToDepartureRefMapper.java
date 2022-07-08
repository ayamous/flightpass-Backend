package ma.itroad.ram.flightpass.model.mapper;


import ma.itroad.ram.flightpass.model.bean.P_DayToDepartureRefBean;
import ma.itroad.ram.flightpass.model.entity.P_DayToDepartureRef;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface P_DayToDepartureRefMapper  {
    @Mapping(target = "configIds", ignore = true)
    P_DayToDepartureRefBean p_BaseConfigBeanEntitytoBean(
            P_DayToDepartureRef p_dayToDepartureRef
    );

    P_DayToDepartureRef p_BaseConfigBeanBeantoEntity(
            P_DayToDepartureRefBean p_dayToDepartureRefBean
    );
}
