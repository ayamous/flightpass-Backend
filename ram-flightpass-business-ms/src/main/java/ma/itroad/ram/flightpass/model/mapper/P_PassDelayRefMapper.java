package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.P_PassDelayRefBean;
import ma.itroad.ram.flightpass.model.entity.P_PassDelayRef;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface P_PassDelayRefMapper {

    P_PassDelayRefBean p_PassDelayRefMapperEntitytoBean(
            P_PassDelayRef p_passDelayRef
    );

    P_PassDelayRef p_PassDelayRefMapperBeantoEntity(
            P_PassDelayRefBean  p_passDelayRefBean
    );
}
