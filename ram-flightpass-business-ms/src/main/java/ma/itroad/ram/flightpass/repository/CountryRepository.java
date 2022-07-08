package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_AirportRef;
import ma.itroad.ram.flightpass.model.entity.P_CountryRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<P_CountryRef, Long> {

    P_CountryRef findByName(String name);
}
