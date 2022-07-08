package ma.itroad.ram.flightpass.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.FPCustomerOrderBean;
import ma.itroad.ram.flightpass.model.bean.FlightPassOverviewBean;
import ma.itroad.ram.flightpass.model.bean.FlightpassVoucher;
import ma.itroad.ram.flightpass.model.bean.PassengerBean;
import ma.itroad.ram.flightpass.model.enums.FpStatus;
import ma.itroad.ram.flightpass.service.EmailService;
import ma.itroad.ram.flightpass.service.FlightPassCustomerOrderService;
import ma.itroad.ram.flightpass.service.UserService;
import ma.itroad.ram.flightpass.service.impl.FlightPassCustomerOrderServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

@RestController

@AllArgsConstructor
public class FlightPassCustomerOrderController {

    private FlightPassCustomerOrderService orderService;
    private FlightPassCustomerOrderServiceImpl flightPassCustomerOrderService;
    private UserService userService;
    private EmailService emailService;


    @PostMapping("/public/flightpass-order/create")
    public ResponseEntity<FPCustomerOrderBean> createOrder(@RequestBody @Valid FPCustomerOrderBean fpCustomerOrderBean){
       return new ResponseEntity<>(orderService.createOrder(fpCustomerOrderBean),HttpStatus.CREATED);
    }

    @PostMapping("/business/flightpass-order/create/{deviceId}")
    public ResponseEntity<FPCustomerOrderBean> createOrder(@RequestBody @Valid PassengerBean passengerBean,
                                                           @PathVariable("deviceId")String deviceId){
        return new ResponseEntity<>(orderService.createOrder(passengerBean,deviceId),HttpStatus.CREATED);
    }

    @GetMapping("/business/flightpass-order/get/{deviceId}")
    public ResponseEntity<FPCustomerOrderBean> getLastOrderConfig(@PathVariable("deviceId") String id){
        return new ResponseEntity<>(flightPassCustomerOrderService.getLastOrderConfig(id),HttpStatus.OK);
    }

    @PostMapping("/public/flightpass-order/voucher")
    public void uploadFiles(@RequestParam("file") MultipartFile multipartFile, FlightpassVoucher voucher){
        voucher.setFirstName("redouane");
        voucher.setLastName("El aouni");
        ObjectMapper oMapper = new ObjectMapper();
        // object -> Map
        Map<String, Object> map = oMapper.convertValue(voucher, Map.class);
        emailService.sendMessageUsingThymeleafTemplate("elaouniredouane@gmail.com",
                "",map,"mail-voucher.html",multipartFile);
    }

    @GetMapping("/business/flightpass-order/active/overview")
    public Page<FlightPassOverviewBean> getActivePasses(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "3") int size,
                                            @RequestParam(defaultValue = "DESC") String sort,
                                            @RequestParam(defaultValue = "createdOn") String ...by){
        Sort.Direction direction = Sort.Direction.fromOptionalString(sort.toUpperCase(Locale.ROOT)).get();

        Pageable pageable = PageRequest.of(page, size, direction, by);
        return flightPassCustomerOrderService.getFlightPassesByUserId(pageable, FpStatus.PAID );
    }

    @GetMapping("/business/flightpass-order/history")
    public Page<FlightPassOverviewBean> getHistoryPasses(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "3") int size,
                                                        @RequestParam(defaultValue = "DESC") String sort,
                                                        @RequestParam(defaultValue = "createdOn") String ...by){
        Sort.Direction direction = Sort.Direction.fromOptionalString(sort.toUpperCase(Locale.ROOT)).get();

        Pageable pageable = PageRequest.of(page, Integer.MAX_VALUE, direction, by);
        return flightPassCustomerOrderService.getFlightPassesByUserId(pageable, null );
    }

}
