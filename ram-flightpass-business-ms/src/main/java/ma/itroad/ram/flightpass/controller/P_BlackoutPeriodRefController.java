package ma.itroad.ram.flightpass.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.P_BlackoutPeriodRefBean;
import ma.itroad.ram.flightpass.service.P_BlackoutPeriodRefService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@AllArgsConstructor
@RestController
public class P_BlackoutPeriodRefController {

    private P_BlackoutPeriodRefService p_blackoutPeriodRefService;


    @GetMapping("/public/blackout/{arrivalAirport}")
    public ResponseEntity<List<P_BlackoutPeriodRefBean>> blackoutPeriodOneWay(@PathVariable("arrivalAirport") String airportCode) {

        return new ResponseEntity<>(p_blackoutPeriodRefService.getBlackoutPeriodOneWay(airportCode), HttpStatus.OK);
    }

    @GetMapping("/public/blackout/{airport1}/{airport2}")
    public ResponseEntity<List<P_BlackoutPeriodRefBean>> blackoutPeriodRoundTrip(@PathVariable("airport1") String airportCode1,
                                                                                 @PathVariable("airport2") String airportCode2) {
        return new ResponseEntity<>(p_blackoutPeriodRefService.getBlackoutPeriodRoundTrip(airportCode1, airportCode2), HttpStatus.OK);
    }

    @PostMapping("/public/blackout")
    public ResponseEntity<P_BlackoutPeriodRefBean> createBlackoutPeriod(@RequestBody @Valid P_BlackoutPeriodRefBean blackoutPeriodDTO) {
        return new ResponseEntity<>(p_blackoutPeriodRefService.createBlackoutPeriod(blackoutPeriodDTO), HttpStatus.CREATED);
    }

    @PutMapping("/public/blackout/{id}")
    public ResponseEntity<P_BlackoutPeriodRefBean> updateBlackoutPeriod(@RequestBody P_BlackoutPeriodRefBean blackoutPeriodDTO,
                                                                        @PathVariable("id") Long id) {
        return new ResponseEntity<>(p_blackoutPeriodRefService.updateBlackoutPeriod(blackoutPeriodDTO,id ), HttpStatus.CREATED);
    }

    @GetMapping("/public/blackout")
    public ResponseEntity<List<P_BlackoutPeriodRefBean>> findAllBlackoutPeriods() {
        return new ResponseEntity<>(p_blackoutPeriodRefService.getAllBlackoutPeriod(), HttpStatus.OK);
    }


    @DeleteMapping("/public/blackout/{id}")
    public void deleteBlackoutPeriod(@PathVariable("id") Long id) {
        p_blackoutPeriodRefService.deleteBlackoutPeriod(id);
    }

}
