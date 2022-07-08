package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_DayToDepartureRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface P_DayToDepartureRefRepository extends JpaRepository<P_DayToDepartureRef,Long> {
    List<P_DayToDepartureRef> findByNbrDays(int nbrDays);
    P_DayToDepartureRef findByNbrDaysAndFlightPassConfigs_Id(int nbrDays, Long configId);
    P_DayToDepartureRef getByNbrDaysAndPercentage(int days ,double percentage);
    void deleteByNbrDays(int days);
    List<P_DayToDepartureRef> findByFlightPassConfigs_Id (Long id);
}
