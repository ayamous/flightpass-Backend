
package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.P_CurrencyRef;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<P_CurrencyRef, Long> {

    P_CurrencyRef findByCode(String code);

}

