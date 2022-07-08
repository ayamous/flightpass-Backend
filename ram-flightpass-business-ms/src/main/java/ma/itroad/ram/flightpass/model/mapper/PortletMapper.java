

package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.PortletBean;
import ma.itroad.ram.flightpass.model.entity.Portlet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortletMapper {
    PortletBean portletEntitytoBean(
            Portlet portlet
    );

    Portlet portletBeantoEntity(
            PortletBean portletBean
    );
}


