package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.core.model.mapper.GenericModelMapper;
import ma.itroad.ram.flightpass.model.bean.PassengerBean;
import ma.itroad.ram.flightpass.model.entity.D_Passenger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper extends GenericModelMapper<D_Passenger, PassengerBean> {

}
