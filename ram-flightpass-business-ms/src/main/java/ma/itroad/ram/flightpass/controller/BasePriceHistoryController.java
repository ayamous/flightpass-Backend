package ma.itroad.ram.flightpass.controller;

import ma.itroad.ram.flightpass.model.bean.BasePriceHistoryBean;
import ma.itroad.ram.flightpass.model.bean.BasePriceRequestBean;
import ma.itroad.ram.flightpass.service.IBasePriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
//@RequestMapping("/business/base-price-history")
public class BasePriceHistoryController {

    @Autowired
    private IBasePriceHistoryService iBasePriceHistoryService;


    @GetMapping("/business/base-price-history/getAll")
    public ResponseEntity<Page<BasePriceHistoryBean>> getAll(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "DESC") String sort,
                                                             @RequestParam(defaultValue = "date") String ...by) {

        Sort.Direction direction = Sort.Direction.fromOptionalString(sort.toUpperCase(Locale.ROOT)).get();

        Pageable pageable = PageRequest.of(page, size, direction, by);

        Page<BasePriceHistoryBean> basePriceBySegment = iBasePriceHistoryService.getAll(pageable);
        return ResponseEntity.ok(basePriceBySegment);
    }


    @GetMapping("/segment/{id}")

    public ResponseEntity<Page<BasePriceHistoryBean>> getBasePriceBySegment(@PathVariable("id") Long id,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            @RequestParam(defaultValue = "DESC") String sort,
                                                                            @RequestParam(defaultValue = "date") String by) {

        Sort.Direction direction = Sort.Direction.fromOptionalString(sort.toUpperCase(Locale.ROOT)).get();

        Pageable pageable = PageRequest.of(page, size, direction, by);

        Page<BasePriceHistoryBean> basePriceBySegment = iBasePriceHistoryService.getBasePriceBySegment(id, pageable);
        return ResponseEntity.ok(basePriceBySegment);
    }

    @PostMapping("/date/segment/currency")
    public BasePriceHistoryBean getBasePriceBySegmentAndCurrency(@RequestBody BasePriceRequestBean requestBean){
        return iBasePriceHistoryService.getBasePriceBySegmentAndCurrency(requestBean);
    }


}
