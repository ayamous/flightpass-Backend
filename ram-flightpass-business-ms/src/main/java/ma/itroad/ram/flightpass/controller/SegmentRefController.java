

package ma.itroad.ram.flightpass.controller;

import ma.itroad.ram.flightpass.model.bean.SegmentBean;
import ma.itroad.ram.flightpass.model.bean.SegmentViewBean;
import ma.itroad.ram.flightpass.service.ISegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
//@RequestMapping("business/segment")
public class SegmentRefController {

    @Autowired
    private ISegmentService iSegmentService;


    @PostMapping("/create")
    public ResponseEntity<?> createSegment(@RequestBody SegmentBean segment){

        return iSegmentService.addSegment(segment);

    }



    @GetMapping("public/segment/getAll")
    public ResponseEntity<Page<SegmentViewBean>> getAll(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "20") int size,
                                                             @RequestParam(defaultValue = "DESC") String sort,
                                                             @RequestParam(defaultValue = "createdOn") String ...by) {

        Sort.Direction direction = Sort.Direction.fromOptionalString(sort.toUpperCase(Locale.ROOT)).get();

        Pageable pageable = PageRequest.of(page, size, direction, by);

        Page<SegmentViewBean> basePriceBySegment = iSegmentService.getAll(pageable);
        return ResponseEntity.ok(basePriceBySegment);
    }







}
