package ma.itroad.ram.flightpass.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.PassengerBean;
import ma.itroad.ram.flightpass.model.entity.D_Passenger;
import ma.itroad.ram.flightpass.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/business/passenger")
public class PassengerController {

    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<D_Passenger> addPassenger(@RequestBody PassengerBean passenger) {
        return new ResponseEntity<>(passengerService.createPassenger(passenger), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<PassengerBean>> getPassengersOfUser() {
        return new ResponseEntity<>(passengerService.getPassengerByUserId(), HttpStatus.OK);
    }

}
