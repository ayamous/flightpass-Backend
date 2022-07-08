
package ma.itroad.ram.flightpass.service;

import ma.itroad.core.service.IBaseService;
import ma.itroad.ram.flightpass.model.bean.BasePriceHistoryBean;
import ma.itroad.ram.flightpass.model.bean.BasePriceRequestBean;
import ma.itroad.ram.flightpass.model.bean.BaseTaxHistoryBean;
import ma.itroad.ram.flightpass.model.entity.D_PriceBaseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBasePriceHistoryService{

    void addBasePriceHistory(BasePriceHistoryBean basePriceHistoryBean);

    BasePriceHistoryBean getBasePriceBySegmentAndCurrency(BasePriceRequestBean priceRequestBean);

    Page<BasePriceHistoryBean> getBasePriceBySegment(Long id, Pageable pageable);

    Page<BasePriceHistoryBean> getAll(Pageable pageable);





}

