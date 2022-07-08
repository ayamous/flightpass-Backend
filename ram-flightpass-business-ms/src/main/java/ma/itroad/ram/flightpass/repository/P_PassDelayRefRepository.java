package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_BaseConfigRef;
import ma.itroad.ram.flightpass.model.entity.P_PassDelayRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface P_PassDelayRefRepository extends JpaRepository<P_PassDelayRef,Long> {
    List<P_PassDelayRef> findByNbrMonths(int NbrMonths);
    P_PassDelayRef findByNbrMonthsAndFlightPassConfigs_Id(int NbrMonths, Long configId);
    P_PassDelayRef findByNbrMonthsAndPercentage(int NbrMonths ,double percentage);
    List<P_PassDelayRef> findByFlightPassConfigs_Id (Long configId);
}
