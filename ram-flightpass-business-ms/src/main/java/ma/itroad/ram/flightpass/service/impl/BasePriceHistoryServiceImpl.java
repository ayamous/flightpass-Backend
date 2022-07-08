package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.BasePriceHistoryBean;
import ma.itroad.ram.flightpass.model.bean.BasePriceRequestBean;
import ma.itroad.ram.flightpass.model.entity.D_PriceBaseHistory;
import ma.itroad.ram.flightpass.model.entity.P_CurrencyRef;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import ma.itroad.ram.flightpass.model.mapper.BasePriceMapper;
import ma.itroad.ram.flightpass.repository.CurrencyRepository;
import ma.itroad.ram.flightpass.repository.PriceBaseHistoryRepository;
import ma.itroad.ram.flightpass.repository.SegmentRepository;
import ma.itroad.ram.flightpass.service.IBasePriceHistoryService;
import ma.itroad.ram.flightpass.service.ISegmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class BasePriceHistoryServiceImpl implements IBasePriceHistoryService {

    private PriceBaseHistoryRepository priceBaseHistoryRepository;
    private SegmentRepository segmentRepository;
    private ISegmentService iSegmentService;
    private CurrencyRepository currencyRepository;
    private BasePriceMapper basePriceMapper;


    @Override
    public void addBasePriceHistory(BasePriceHistoryBean basePriceHistoryBean) {

        String arrivalAirportCode = basePriceHistoryBean.getArrivalAirportCode();
        String departureAirportCode = basePriceHistoryBean.getDepartureAirportCode();
        String currencyCode = basePriceHistoryBean.getCurrencyCode();

        boolean segmentExist = iSegmentService.
                segmentExist(arrivalAirportCode,
                        departureAirportCode);

        if (!segmentExist) {
            System.out.println("Segment does not Exist");
            return;
        }

        P_CurrencyRef currencyRef = currencyRepository.findByCode(currencyCode);


        if (currencyRef == null) {
            return;
        }

        P_SegmentRef segmentRef = iSegmentService.
                getSegmentByAirportsCode(arrivalAirportCode, departureAirportCode);

        if(segmentRef == null){

            segmentRef = iSegmentService.
                    getSegmentByAirportsCode(departureAirportCode,arrivalAirportCode);

        }

        Set<D_PriceBaseHistory> priceBaseHistories = segmentRef.getPriceBaseHistories();
        D_PriceBaseHistory priceBaseHistory = new D_PriceBaseHistory();
        priceBaseHistory.setAmount(basePriceHistoryBean.getAmount());
        priceBaseHistory.setDate(basePriceHistoryBean.getDate());
        priceBaseHistory.setSegment(segmentRef);
        priceBaseHistory.setCurrencyRef(currencyRef);
        priceBaseHistories.add(priceBaseHistory);
        segmentRepository.save(segmentRef);

    }

    @Override
    public BasePriceHistoryBean getBasePriceBySegmentAndCurrency(BasePriceRequestBean priceRequestBean) {

        String currencyCode = priceRequestBean.getCurrencyCode();
        LocalDate date = LocalDate.now();

        Long id = priceRequestBean.getSegmentId();

        Optional<P_SegmentRef> p_segmentRefOptional = segmentRepository.findById(id);
        P_CurrencyRef currencyRef = currencyRepository.findByCode(currencyCode);

        if (!p_segmentRefOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment Not Found");
        }

        if (currencyRef == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency Not Found");
        }

        P_SegmentRef segmentRef = p_segmentRefOptional.get();
        D_PriceBaseHistory d_priceBaseHistory = priceBaseHistoryRepository.findByDateAndCurrencyRefAndSegment(date,currencyRef,segmentRef);

        if(d_priceBaseHistory == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "base Price not found");
        }

        BasePriceHistoryBean basePriceHistoryBean = basePriceMapper.entityToBean(d_priceBaseHistory);




        return basePriceHistoryBean;
    }

    @Override
    public Page<BasePriceHistoryBean> getBasePriceBySegment(Long id, Pageable pageable) {
        return priceBaseHistoryRepository.
                findBySegmentId(id, pageable).map(priceBaseHistory ->
                basePriceMapper.entityToBean(priceBaseHistory));
    }

    @Override
    public Page<BasePriceHistoryBean> getAll(Pageable pageable) {
        return priceBaseHistoryRepository.findAll(pageable).map(priceBaseHistory ->
                basePriceMapper.entityToBean(priceBaseHistory));
    }

}
