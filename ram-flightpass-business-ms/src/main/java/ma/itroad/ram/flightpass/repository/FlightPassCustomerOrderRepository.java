package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.D_FlightPassCustomerOrder;
import ma.itroad.ram.flightpass.model.enums.FpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FlightPassCustomerOrderRepository extends JpaRepository<D_FlightPassCustomerOrder, Long> {

    D_FlightPassCustomerOrder findTopByFpOrderDateAndDeviceIdOrderByCreatedOnDesc(LocalDate date, String deviceId);
    @Query("select fp from D_FlightPassCustomerOrder fp where fp.fpUserRef = :user and fp.fpStatus <> 'CREATED' and " + "(:status is null or  fp.fpStatus = :status)")
    Page<D_FlightPassCustomerOrder> findByFpUserRefAndFpStatus(@Param("user") String fpUserRef,
                                                               @Param("status") FpStatus fpStatus,
                                                               Pageable pageable);


    List<D_FlightPassCustomerOrder> findD_FlightPassCustomerOrderByFpStatus(FpStatus fpStatus);
}
