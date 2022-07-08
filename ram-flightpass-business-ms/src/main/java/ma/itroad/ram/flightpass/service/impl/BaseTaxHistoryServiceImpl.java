package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.BaseTaxHistoryBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxRequestBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxRequestByDirectionBean;
import ma.itroad.ram.flightpass.model.entity.D_TaxBaseHistory;
import ma.itroad.ram.flightpass.model.entity.P_AirportRef;
import ma.itroad.ram.flightpass.model.entity.P_CurrencyRef;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import ma.itroad.ram.flightpass.model.mapper.BaseTaxMapper;
import ma.itroad.ram.flightpass.repository.AirportRepository;
import ma.itroad.ram.flightpass.repository.CurrencyRepository;
import ma.itroad.ram.flightpass.repository.SegmentRepository;
import ma.itroad.ram.flightpass.repository.TaxBaseHistoryRepository;
import ma.itroad.ram.flightpass.service.IBaseTaxHistoryService;
import ma.itroad.ram.flightpass.service.ISegmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class BaseTaxHistoryServiceImpl implements IBaseTaxHistoryService {


    private ISegmentService iSegmentService;
    private CurrencyRepository currencyRepository;
    private SegmentRepository segmentRepository;
    private AirportRepository airportRepository;
    private TaxBaseHistoryRepository taxBaseHistoryRepository;
    private BaseTaxMapper baseTaxMapper;


    @Override
    public void addTaxBaseHistory(BaseTaxHistoryBean baseTaxHistoryBean) {

        String arrivalAirportCode = baseTaxHistoryBean.getArrivalAirportCode();
        String departureAirportCode = baseTaxHistoryBean.getDepartureAirportCode();
        String currencyCode = baseTaxHistoryBean.getCurrencyCode();

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

        if (segmentRef == null) {
            segmentRef = iSegmentService.
                    getSegmentByAirportsCode(departureAirportCode, arrivalAirportCode);
        }

        Set<D_TaxBaseHistory> priceBaseHistories = segmentRef.getTaxBaseHistories();
        D_TaxBaseHistory taxBaseHistory = new D_TaxBaseHistory();
        taxBaseHistory.setAmount(baseTaxHistoryBean.getAmount());
        taxBaseHistory.setDate(baseTaxHistoryBean.getDate());
        taxBaseHistory.setSegment(segmentRef);
        taxBaseHistory.setCurrencyRef(currencyRef);
        taxBaseHistory.setFlightFrom(airportRepository.findByCode(baseTaxHistoryBean.getFlightFrom()));
        priceBaseHistories.add(taxBaseHistory);
        segmentRepository.save(segmentRef);

    }

    @Override
    public List<BaseTaxHistoryBean> getTaxesBySegmentAndCurrencyAndDate(BaseTaxRequestBean requestBean) {

        List<D_TaxBaseHistory> taxes = taxBaseHistoryRepository.
                findByCurrencyRefCodeAndDateAndSegmentId(requestBean.getCurrencyCode(),
                LocalDate.now(), requestBean.getSegmentId());

        if(taxes.size() <1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "base Tax not found");
        }

        List<BaseTaxHistoryBean> baseTaxHistoryBeans = baseTaxMapper.entityToBeanList(taxes);


        OptionalDouble optionalDouble = taxes.stream().mapToDouble(D_TaxBaseHistory::getAmount).average();

        return baseTaxHistoryBeans;
    }

    @Override
    public Page<BaseTaxHistoryBean> getTaxesBySegment(Long id, Pageable pageable) {
        return taxBaseHistoryRepository.
                findBySegmentId(id, pageable).map(baseTaxMapper::entityToBean);
    }

    @Override
    public Page<BaseTaxHistoryBean> getAll(Pageable pageable) {
        return taxBaseHistoryRepository.findAll(pageable).map(baseTaxMapper::entityToBean);
    }

    @Override
    public BaseTaxHistoryBean getBaseTaxByDirection(BaseTaxRequestByDirectionBean requestByDirectionBean) {

        String departureAirportCode = requestByDirectionBean.getDepartureAirportCode();
        String arrivalAirportCode = requestByDirectionBean.getArrivalAirportCode();
        String currencyCode = requestByDirectionBean.getCurrencyCode();
        LocalDate date = requestByDirectionBean.getDate();

        P_SegmentRef segmentRef = iSegmentService.
                getSegmentByAirportsCode(arrivalAirportCode, departureAirportCode);
        P_CurrencyRef p_currencyRef = currencyRepository.findByCode(currencyCode);
        P_AirportRef departureAirportRef = airportRepository.findByCode(departureAirportCode);
        P_AirportRef arrivalAirportRef = airportRepository.findByCode(arrivalAirportCode);

        if(segmentRef == null){

            segmentRef = iSegmentService.
                    getSegmentByAirportsCode(departureAirportCode,arrivalAirportCode);

        }

        if(segmentRef == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Segment Not found");
        }

        if(p_currencyRef == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Currency Not found");
        }

        if(departureAirportRef == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"departure Airport Not found");
        }
        if(arrivalAirportRef == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"arrival Airport Not found");
        }

        D_TaxBaseHistory d_taxBaseHistory = taxBaseHistoryRepository
                .findBySegmentAndDateAndCurrencyRefAndFlightFrom
                    (segmentRef, date, p_currencyRef, departureAirportRef);


        if (d_taxBaseHistory == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"tax not found for these criteria");
        }

        BaseTaxHistoryBean baseTaxBean = new BaseTaxHistoryBean();

        baseTaxBean.setDepartureAirportCode(d_taxBaseHistory.getFlightFrom().getCode());
        baseTaxBean.setArrivalAirportCode(arrivalAirportRef.getCode());
        baseTaxBean.setDate(d_taxBaseHistory.getDate());
        baseTaxBean.setCurrencyCode(d_taxBaseHistory.getCurrencyRef().getCode());
        baseTaxBean.setAmount(d_taxBaseHistory.getAmount());


        return baseTaxBean;
    }
}
