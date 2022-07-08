package ma.itroad.ram.flightpass.model.mapper;


import ma.itroad.ram.flightpass.model.bean.P_BaseConfigRefBean;
import ma.itroad.ram.flightpass.model.entity.P_BaseConfigRef;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface P_BaseConfigRefMapper {

    P_BaseConfigRefBean p_BaseConfigBeanEntitytoBean(
            P_BaseConfigRef p_baseConfigRef
    );

    P_BaseConfigRef p_BaseConfigBeanBeantoEntity(
            P_BaseConfigRefBean p_baseConfigRefBean
    );
}
