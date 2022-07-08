package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.BaseTaxHistoryBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxRequestBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxRequestByDirectionBean;
import ma.itroad.ram.flightpass.model.entity.D_PriceBaseHistory;
import ma.itroad.ram.flightpass.model.entity.D_TaxBaseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IBaseTaxHistoryService {

    void addTaxBaseHistory(BaseTaxHistoryBean baseTaxHistoryBean);

    List<BaseTaxHistoryBean> getTaxesBySegmentAndCurrencyAndDate(BaseTaxRequestBean baseTaxRequestBean);

    Page<BaseTaxHistoryBean> getTaxesBySegment(Long id, Pageable pageable);

    Page<BaseTaxHistoryBean> getAll(Pageable pageable);

    BaseTaxHistoryBean getBaseTaxByDirection(BaseTaxRequestByDirectionBean requestByDirectionBean);
}
