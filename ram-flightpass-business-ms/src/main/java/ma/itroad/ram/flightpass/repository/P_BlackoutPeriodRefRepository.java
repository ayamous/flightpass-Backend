package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_BlackoutPeriodRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface P_BlackoutPeriodRefRepository extends JpaRepository<P_BlackoutPeriodRef, Long> {

    P_BlackoutPeriodRef findByReferenceBlackout(String ref);
}
