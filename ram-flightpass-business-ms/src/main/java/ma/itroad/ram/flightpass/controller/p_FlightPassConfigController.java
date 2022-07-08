
package ma.itroad.ram.flightpass.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.LandingPageConfigBean;
import ma.itroad.ram.flightpass.model.bean.P_FlightPassConfigBean;
import ma.itroad.ram.flightpass.model.entity.P_FlightPassConfig;
import ma.itroad.ram.flightpass.service.P_FlightPassConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class p_FlightPassConfigController {

    private P_FlightPassConfigService p_flightPassConfigService;


    @PostMapping(path = "api/config")
    public ResponseEntity<P_FlightPassConfig> postFlightPassConfig(@RequestBody P_FlightPassConfigBean p_flightPassConfigBean) {
        return new ResponseEntity(p_flightPassConfigService.postFlightPassConfig(p_flightPassConfigBean.getReference(), p_flightPassConfigBean.getP_AirportRef_id()), HttpStatus.CREATED);
    }

    @GetMapping(path = "api/config")
    public ResponseEntity<List<P_FlightPassConfig>> getFlightPassConfig(@RequestParam(required = false) String reference) {
        if (reference != null) {
            return new ResponseEntity(p_flightPassConfigService.getFlightPassConfigByReference(reference), HttpStatus.OK);

        } else {
            return new ResponseEntity(p_flightPassConfigService.getFlightPassConfigs(), HttpStatus.OK);
        }
    }

    @GetMapping(path = "api/config/{configId}")
    public ResponseEntity<P_FlightPassConfig> getFlightPassConfigById(@PathVariable Long configId) {
        return new ResponseEntity(p_flightPassConfigService.getFlightPassConfigById(configId), HttpStatus.OK);
    }


    @PatchMapping(path = "api/config/{configId}")
    public ResponseEntity<P_FlightPassConfig> patchFlightPassConfigById(@PathVariable Long configId, @RequestBody P_FlightPassConfigBean p_flightPassConfigBean) {
        return new ResponseEntity(p_flightPassConfigService.patchFlightPassConfig(configId, p_flightPassConfigBean.isEnabled()), HttpStatus.OK);
    }

    @GetMapping(path = "api/config/defaultConfig/{configId}/{segmentId}/{currency}")
    public ResponseEntity<LandingPageConfigBean> getDefaultConfig(@PathVariable("configId") Long configId,
                                                                  @PathVariable("segmentId") Long segmentId,
                                                                  @PathVariable("currency") String currency) {

        return new ResponseEntity<>(p_flightPassConfigService.getDefaultConfig(configId, segmentId,currency ), HttpStatus.OK);
    }

}


