package ma.itroad.ram.flightpass.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.enums.FpStatus;
import ma.itroad.ram.flightpass.repository.FlightPassCustomerOrderRepository;
import ma.itroad.ram.flightpass.service.EmailService;
import ma.itroad.ram.flightpass.service.UserService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class MailScheduler {

    private static final String DAILY = "@daily";
    private final String template = "mail-expiration.html";

    private FlightPassCustomerOrderRepository orderRepository;
    private UserService userService;
    private EmailService emailService;

    @Scheduled(cron = DAILY)
    public void mailJob() {
        orderRepository.findD_FlightPassCustomerOrderByFpStatus(FpStatus.PAID)
                .stream()
                .filter(order -> LocalDate.now().plusDays(10).isEqual(order.getFpExpiryDate()))
                .forEach(order -> {
                    UserRepresentation user = userService.getUserById(order.getFpUserRef());
                    String email = user.getEmail();
                    System.out.println("Sending mail to " + email + "  ...........");
                    ObjectMapper oMapper = new ObjectMapper();
                    // object -> Map
                    Map<String, Object> map = oMapper.convertValue(user, Map.class);
                    emailService.sendMessageUsingThymeleafTemplate("elaouniredouane@gmail.com",
                            "",map, template,null);
                });


    }

    @Scheduled(cron = DAILY)
    public void priceJob() {
        System.out.println("Price job at : " + LocalDateTime.now());
    }

    @Scheduled(cron = DAILY)
    public void expiryFP(){
        orderRepository.findD_FlightPassCustomerOrderByFpStatus(FpStatus.PAID)
                .stream()
                .filter(order -> LocalDate.now().isEqual(order.getFpExpiryDate()))
                .forEach(order -> order.setFpStatus(FpStatus.EXPIRED));
    }
}
