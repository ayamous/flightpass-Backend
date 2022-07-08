package ma.itroad.ram.flightpass.model.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.itroad.ram.flightpass.exception.ConfiguratioNotFound;
import ma.itroad.ram.flightpass.exception.FlightPassConfigNotFound;
import ma.itroad.ram.flightpass.repository.P_FlightPassConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class P_FlightPassConfigBean {
    private Long Id ;
    private String reference;
    private boolean enabled;
    private Date effectiveDate;
    private Long p_AirportRef_id;







}
