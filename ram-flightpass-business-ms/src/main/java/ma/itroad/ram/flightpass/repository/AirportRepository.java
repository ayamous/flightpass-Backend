package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_AirportRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<P_AirportRef,Long> {
    P_AirportRef findByCode(String code);
}
