package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_FlightPassConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface P_FlightPassConfigRepository extends JpaRepository<P_FlightPassConfig,Long> {
    List<P_FlightPassConfig> findByEnabled(boolean enabled);
    P_FlightPassConfig findByReference(String reference);

}
