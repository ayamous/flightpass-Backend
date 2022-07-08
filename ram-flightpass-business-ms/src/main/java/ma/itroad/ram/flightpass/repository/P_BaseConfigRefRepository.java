package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_BaseConfigRef;
import ma.itroad.ram.flightpass.model.entity.P_BaseConfigRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface P_BaseConfigRefRepository extends JpaRepository<P_BaseConfigRef, Long> {
    List<P_BaseConfigRef> findByNbrFlights(int NbrFlights);
    P_BaseConfigRef findByNbrFlightsAndFlightPassConfigs_Id(int NbrFlights, Long configId);
    P_BaseConfigRef findByNbrFlightsAndPercentage(int NbrFlights ,double percentage);
    List<P_BaseConfigRef> findByFlightPassConfigs_Id (Long id);

}
