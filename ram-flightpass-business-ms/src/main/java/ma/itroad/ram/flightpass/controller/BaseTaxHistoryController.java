package ma.itroad.ram.flightpass.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.BaseTaxHistoryBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxRequestBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxRequestByDirectionBean;
import ma.itroad.ram.flightpass.service.IBaseTaxHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/business/base-tax-history")
@AllArgsConstructor
public class BaseTaxHistoryController {

    IBaseTaxHistoryService iBaseTaxHistoryService;

    @GetMapping("/getAll")
    public ResponseEntity<Page<BaseTaxHistoryBean>> getAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "DESC") String sort,
                                                           @RequestParam(defaultValue = "date") String ...by) {

        Sort.Direction direction = Sort.Direction.fromOptionalString(sort.toUpperCase(Locale.ROOT)).get();

        Pageable pageable = PageRequest.of(page, size, direction, by);

        Page<BaseTaxHistoryBean> basePriceBySegment = iBaseTaxHistoryService.getAll(pageable);
        return ResponseEntity.ok(basePriceBySegment);
    }

    @PostMapping("/date/segment/currency")
    public List<BaseTaxHistoryBean> getTaxesBySegmentAndCurrencyAndDate(@RequestBody BaseTaxRequestBean requestBean){
        return iBaseTaxHistoryService.getTaxesBySegmentAndCurrencyAndDate(requestBean);
    }

    @GetMapping("/segment/{id}")

    public ResponseEntity<Page<BaseTaxHistoryBean>> getBaseTaxBySegment(@PathVariable("id") Long id,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            @RequestParam(defaultValue = "DESC") String sort,
                                                                            @RequestParam(defaultValue = "date") String by) {

        Sort.Direction direction = Sort.Direction.fromOptionalString(sort.toUpperCase(Locale.ROOT)).get();

        Pageable pageable = PageRequest.of(page, size, direction, by);

        Page<BaseTaxHistoryBean> basePriceBySegment = iBaseTaxHistoryService.getTaxesBySegment(id, pageable);
        return ResponseEntity.ok(basePriceBySegment);
    }

    @PostMapping("/by-direction")
    public BaseTaxHistoryBean getBaseTaxByDirection(@RequestBody BaseTaxRequestByDirectionBean taxRequestByDirectionBean){
        return iBaseTaxHistoryService.getBaseTaxByDirection(taxRequestByDirectionBean);
    }
}
