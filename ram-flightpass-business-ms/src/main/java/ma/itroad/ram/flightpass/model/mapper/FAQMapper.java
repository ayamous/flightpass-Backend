package ma.itroad.ram.flightpass.model.mapper;

import ma.itroad.ram.flightpass.model.bean.FAQBean;
import ma.itroad.ram.flightpass.model.entity.FAQ;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FAQMapper {
    FAQBean FAQEntitytoBean(
            FAQ faq
    );

    FAQ FAQBeantoEntity(
            FAQBean faqBean
    );


}
