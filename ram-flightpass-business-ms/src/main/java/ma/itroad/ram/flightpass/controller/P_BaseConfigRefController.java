package ma.itroad.ram.flightpass.controller;


import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.P_BaseConfigRefBean;
import ma.itroad.ram.flightpass.service.P_BaseConfigRefService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class P_BaseConfigRefController {

    private P_BaseConfigRefService p_baseConfigRefService;


    @PostMapping(path = "/api/config/baseConfig")
    public ResponseEntity<HttpStatus> postBaseConfigRef(@RequestParam(required = false)  Long configId , @RequestBody P_BaseConfigRefBean p_baseConfigRefBean)  {
        p_baseConfigRefService.postBaseConfigRef(configId ,p_baseConfigRefBean.getNbrFlights() , p_baseConfigRefBean.getPercentage());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(path ="/api/config/baseConfig" )
    public ResponseEntity<List<P_BaseConfigRefBean>> geBaseConfigRefs(@RequestParam(required = false) Long configId){
        if (configId !=null){
            return new ResponseEntity(p_baseConfigRefService.getBaseConfigRefByConfigId(configId),HttpStatus.OK);
        }
        else{
            return new ResponseEntity(p_baseConfigRefService.getBaseConfigRef(),HttpStatus.OK);
        }
    }

    @GetMapping("/api/config/baseConfig/maximum")
    public ResponseEntity<P_BaseConfigRefBean> getMaxBaseConfig(@RequestParam(required = true) Long configId){
        return new ResponseEntity<>(p_baseConfigRefService.maxBaseConfig(configId),HttpStatus.OK);
    }
    @GetMapping("/api/config/baseConfig/minimum")
    public ResponseEntity<P_BaseConfigRefBean> getMinBaseConfig(@RequestParam(required = true) Long configId){
        return new ResponseEntity<>(p_baseConfigRefService.minBaseConfig(configId),HttpStatus.OK);
    }

    @GetMapping(path = "/api/config/baseConfig/{nbrFlights}")
    public ResponseEntity<P_BaseConfigRefBean> getBaseConfigRef(@RequestParam(value ="configId" , required = false) Long configId ,@PathVariable int nbrFlights){
        if (configId != null){
            return new ResponseEntity(p_baseConfigRefService.getBaseConfigRefByNbrFlightsAndConfigId(configId,nbrFlights),HttpStatus.OK);
        }
        else{
            return new ResponseEntity(p_baseConfigRefService.getBaseConfigRefByNbrFlights(nbrFlights),HttpStatus.OK);
        }
    }

    @PatchMapping(path = "/api/config/baseConfig/{nbrFlights}")
    public ResponseEntity<P_BaseConfigRefBean> pathBaseConfigRef(@RequestParam(value = "configId" ) Long configId , @PathVariable int nbrFlights , @RequestBody P_BaseConfigRefBean p_baseConfigRefBean ){
        return new ResponseEntity(p_baseConfigRefService.patchBaseConfigRef(configId , nbrFlights , p_baseConfigRefBean.getPercentage() ),HttpStatus.OK);
    }

    @DeleteMapping(path = "api/config/baseConfig/{nbrFlights}")
    public ResponseEntity<HttpStatus> deleteBaseConfigRefByConfig(@RequestParam(value = "configId" ) Long configId , @PathVariable int nbrFlights){
        p_baseConfigRefService.deleteBaseConfigRefByNbrFlightsAndConfigId(configId ,nbrFlights);
        return new ResponseEntity(HttpStatus.OK);
    }





}
