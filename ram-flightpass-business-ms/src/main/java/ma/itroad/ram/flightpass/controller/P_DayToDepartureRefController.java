package ma.itroad.ram.flightpass.controller;


import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.P_DayToDepartureRefBean;
import ma.itroad.ram.flightpass.model.bean.P_PassDelayRefBean;
import ma.itroad.ram.flightpass.service.P_DayToDepartureRefService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class P_DayToDepartureRefController {

    private P_DayToDepartureRefService p_dayToDepartureRefService;



  @PostMapping(path = "/api/config/dayToDeparture")
    public ResponseEntity<HttpStatus> postDayToDepartureRef(@RequestParam(required = false)  Long configId , @RequestBody P_DayToDepartureRefBean p_dayToDepartureRefBean)  {
      p_dayToDepartureRefService.postDayToDepartureRef(configId ,p_dayToDepartureRefBean.getNbrDays() , p_dayToDepartureRefBean.getPercentage());
      return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping(path ="/api/config/dayToDeparture" )
    public ResponseEntity<List<P_PassDelayRefBean>> getDayToDepartureRefs(@RequestParam(required = false) Long configId){
       if (configId !=null){
           return new ResponseEntity(p_dayToDepartureRefService.getDayToDepartureRefByConfigId(configId),HttpStatus.OK);
       }
       else{
           return new ResponseEntity(p_dayToDepartureRefService.getDayToDepartureRef(),HttpStatus.OK);
       }
    }

    @GetMapping("/api/config/dayToDeparture/maximum")
    public ResponseEntity<P_DayToDepartureRefBean> getMaxDayToDeparture(@RequestParam(required = true) Long configId){
        return new ResponseEntity<>(p_dayToDepartureRefService.getMaxDayToDeparture(configId),HttpStatus.OK);
    }
    @GetMapping("/api/config/dayToDeparture/minimum")
    public ResponseEntity<P_DayToDepartureRefBean> getMinDayToDeparture(@RequestParam(required = true) Long configId){
        return new ResponseEntity<>(p_dayToDepartureRefService.getMinDayToDeparture(configId),HttpStatus.OK);
    }

    @GetMapping(path = "/api/config/dayToDeparture/{nbrDays}")
    public ResponseEntity<P_DayToDepartureRefBean> getDayToDepartureRef(@RequestParam(value ="configId" , required = false) Long configId ,@PathVariable int nbrDays){
       if (configId != null){
           return new ResponseEntity(p_dayToDepartureRefService.getDayToDepartureRefByNbrDaysAndConfigId(configId ,nbrDays),HttpStatus.OK);
       }
       else{
           return new ResponseEntity(p_dayToDepartureRefService.getDayToDepartureRefByNbrDays(nbrDays),HttpStatus.OK);
       }
    }

    @PatchMapping(path = "/api/config/dayToDeparture/{nbrDays}")
    public ResponseEntity<P_DayToDepartureRefBean> pathDayToDepartureRef(@RequestParam(value = "configId",required = false ) Long configId , @PathVariable int nbrDays , @RequestBody P_DayToDepartureRefBean p_dayToDepartureRefBean ){
      return new ResponseEntity(p_dayToDepartureRefService.patchDayToDepartureRef(configId , nbrDays , p_dayToDepartureRefBean.getPercentage() ),HttpStatus.OK);
    }

    @DeleteMapping(path = "api/config/dayToDeparture/{nbrDays}")
    public ResponseEntity<HttpStatus> deleteDayToDepartureRefbyConfig(@RequestParam(value = "configId" ) Long configId , @PathVariable int nbrDays){
      p_dayToDepartureRefService.deleteDayToDepartureRefByNbrDaysAndConfigId(configId ,nbrDays);
      return new ResponseEntity(HttpStatus.OK);
    }





}
