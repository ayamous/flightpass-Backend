
package ma.itroad.ram.flightpass.controller;

import ma.itroad.ram.flightpass.repository.AirportRepository;
import ma.itroad.ram.flightpass.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business/airport")
public class AirportRefController {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AirportRepository airportRepository;

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        airportRepository.deleteById(id);
    }


}

