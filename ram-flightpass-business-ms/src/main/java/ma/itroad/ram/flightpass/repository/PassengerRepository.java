package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.D_Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<D_Passenger,Long> {

    D_Passenger findByEmailAndUserAccountId(String email, String id);
    D_Passenger findByEmail(String email);
    List<D_Passenger> findByUserAccountIdAndPrincipalUserAccountIsFalse(String userId);
}
