package ma.itroad.ram.flightpass.controller;


import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.P_PassDelayRefBean;
import ma.itroad.ram.flightpass.service.P_PassDelayRefService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class P_PassDelayRefController {

    private P_PassDelayRefService  p_passDelayRefService;

    @PostMapping(path = "/api/config/passDelay")
    public ResponseEntity<HttpStatus> postPassDelayRef(@RequestParam(required = false)  Long configId , @RequestBody P_PassDelayRefBean p_passDelayRefBean)  {
        p_passDelayRefService.postPassDelayRef(configId ,p_passDelayRefBean.getNbrMonths() , p_passDelayRefBean.getPercentage());
        return new ResponseEntity(HttpStatus.CREATED);

    }

    @GetMapping(path ="/api/config/passDelay" )
    public ResponseEntity<List<P_PassDelayRefBean>> gePassDelayRef(@RequestParam(required = false) Long configId){
        if (configId !=null){
            return new ResponseEntity(p_passDelayRefService.getPassDelayRefByConfigId(configId),HttpStatus.OK);
        }
        else{
            return new ResponseEntity(p_passDelayRefService.getPassDelayRef(),HttpStatus.OK);
        }
        //
    }

    @GetMapping("/api/config/passDelay/maximum")
    public ResponseEntity<P_PassDelayRefBean> getMaxPassDelay(@RequestParam(required = true) Long configId){
        return new ResponseEntity<>(p_passDelayRefService.getMaxPassDelay(configId),HttpStatus.OK);
    }
    @GetMapping("/api/config/passDelay/minimum")
    public ResponseEntity<P_PassDelayRefBean> getMinPassDelay(@RequestParam(required = true) Long configId){
        return new ResponseEntity<>(p_passDelayRefService.getMinPassDelay(configId),HttpStatus.OK);
    }

    @GetMapping(path = "/api/config/passDelay/{nbrMonths}")
    public ResponseEntity<P_PassDelayRefBean> getPassDelayRef(@RequestParam(value ="configId" , required = false) Long configId ,@PathVariable int nbrMonths){
        if (configId != null){
            return new ResponseEntity(p_passDelayRefService.getPassDelayRefByNbrMonthsAndConfigId(configId, nbrMonths),HttpStatus.OK);
        }
        else{
            return new ResponseEntity(p_passDelayRefService.getPassDelayRefByNbrMonths(nbrMonths),HttpStatus.OK);
        }
    }

    @PatchMapping(path = "/api/config/passDelay/{nbrMonths}")
    public ResponseEntity<P_PassDelayRefBean> patchPassDelayRef(@RequestParam(value = "configId" ) Long configId , @PathVariable int nbrMonths , @RequestBody P_PassDelayRefBean p_passDelayRefBean ){
        return new ResponseEntity(p_passDelayRefService.patchPassDelayRefByConfigId(configId , nbrMonths , p_passDelayRefBean.getPercentage() ),HttpStatus.OK);
    }

    @DeleteMapping(path = "api/config/passDelay/{nbrMonths}")
    public ResponseEntity<HttpStatus> deletePassDelayRefByConfig(@RequestParam(value = "configId" ) Long configId , @PathVariable int nbrMonths){
        p_passDelayRefService.deletePassDelayRefByNbrMonthsAndConfigId(configId ,nbrMonths);
        return new ResponseEntity(HttpStatus.OK);
    }



}
