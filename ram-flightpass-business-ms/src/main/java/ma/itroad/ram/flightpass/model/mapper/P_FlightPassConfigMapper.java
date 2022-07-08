package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.P_BaseConfigRefBean;
import ma.itroad.ram.flightpass.model.bean.P_FlightPassConfigBean;
import ma.itroad.ram.flightpass.model.entity.P_BaseConfigRef;
import ma.itroad.ram.flightpass.model.entity.P_FlightPassConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface P_FlightPassConfigMapper {
    P_FlightPassConfigBean p_FlightPassConfigEntityToBean(
            P_FlightPassConfig p_flightPassConfig
    );

    P_FlightPassConfig p_FlightPassConfigBeanToEntity(
            P_FlightPassConfigBean p_flightPassConfigBean
    );
}
