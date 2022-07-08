package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_AirportRef;
import ma.itroad.ram.flightpass.model.entity.P_CityRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<P_CityRef,Long> {

    P_CityRef findByName(String name);
}
