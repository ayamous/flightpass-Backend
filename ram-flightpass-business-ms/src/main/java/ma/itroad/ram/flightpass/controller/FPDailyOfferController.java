package ma.itroad.ram.flightpass.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.LandingPageConfigBean;
import ma.itroad.ram.flightpass.service.LPFPDailyOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/business/fp-dailyOffer")
@AllArgsConstructor
public class FPDailyOfferController {


    private LPFPDailyOfferService dailyOfferService;

    @GetMapping("public/fp-dailyOffer/defaultConfig/{segmentId}/{currency}")
    public ResponseEntity<LandingPageConfigBean> getLandingPageConfig(@PathVariable("segmentId") Long segmentId,@PathVariable("currency") String currencyCode){
        LandingPageConfigBean landingPageConfig = dailyOfferService.getLandingPageConfig(segmentId, currencyCode);
        return new ResponseEntity<>(landingPageConfig, HttpStatus.OK);
    }
}
