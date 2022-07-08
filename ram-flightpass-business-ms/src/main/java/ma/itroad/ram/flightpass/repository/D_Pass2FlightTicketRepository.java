package ma.itroad.ram.flightpass.repository;

import ma.itroad.ram.flightpass.model.entity.D_FlightPassCustomerOrder;
import ma.itroad.ram.flightpass.model.entity.D_Pass2FlightTicket;
import ma.itroad.ram.flightpass.model.enums.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface D_Pass2FlightTicketRepository extends JpaRepository<D_Pass2FlightTicket,Long> {

    int countByFpOrderAndCouponStatus(D_FlightPassCustomerOrder order, CouponStatus couponStatus);
    int countByFpOrder(D_FlightPassCustomerOrder order);
    List<D_Pass2FlightTicket> findByFpOrderAndCouponStatus(D_FlightPassCustomerOrder order, CouponStatus couponStatus);
    List<D_Pass2FlightTicket> findByFpOrderAndFlightReservationNotNull(D_FlightPassCustomerOrder order);
}
