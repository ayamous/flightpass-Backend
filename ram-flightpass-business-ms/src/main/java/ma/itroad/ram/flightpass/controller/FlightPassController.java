
package ma.itroad.ram.flightpass.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.*;
import ma.itroad.ram.flightpass.service.FlightPassService;
import ma.itroad.ram.flightpass.service.IBasePriceHistoryService;
import ma.itroad.ram.flightpass.service.IBaseTaxHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/business/flightpass")
@AllArgsConstructor
public class FlightPassController {

    private IBaseTaxHistoryService iBaseTaxHistoryService;

    private IBasePriceHistoryService iBasePriceHistoryService;

    private FlightPassService flightPassService;


    @PostMapping("/tax")
    public List<BaseTaxHistoryBean> getTaxesBySegmentAndCurrencyAndDate(@RequestBody BaseTaxRequestBean requestBean){
        return iBaseTaxHistoryService.getTaxesBySegmentAndCurrencyAndDate(requestBean);
    }


    @PostMapping("/price")
    public BasePriceHistoryBean getBasePriceBySegmentAndCurrency(@RequestBody BasePriceRequestBean requestBean){
        return iBasePriceHistoryService.getBasePriceBySegmentAndCurrency(requestBean);
    }

    @PostMapping("public/flightpass/flightpass-price")

    public ResponseEntity<FlightPassBean> calculateFlightPassPrice(@RequestBody FlightPassPriceRequestBean passPriceBean){
//        KeycloakProvider keycloakProvider = new KeycloakProvider();
////        System.out.println("user name "+keycloakProvider.getConnectedUser());
      return new ResponseEntity<>(flightPassService.calculateFlightPassPrice(passPriceBean), HttpStatus.OK);
    }






}

