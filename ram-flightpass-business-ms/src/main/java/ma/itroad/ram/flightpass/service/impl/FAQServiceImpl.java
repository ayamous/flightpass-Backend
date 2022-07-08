package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.FAQBean;
import ma.itroad.ram.flightpass.model.entity.FAQ;
import ma.itroad.ram.flightpass.model.mapper.FAQMapper;
import ma.itroad.ram.flightpass.repository.FAQRepository;
import ma.itroad.ram.flightpass.service.FAQService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class FAQServiceImpl implements FAQService {

    private FAQRepository faqRepository;
    private FAQMapper faqMapper;


    @Override
    public FAQBean postFAQ(String question, String answer) {
        FAQ faq = faqRepository.save(new FAQ(question, answer));
        return faqMapper.FAQEntitytoBean(faq);
    }

    @Override
    public List<FAQBean> getFAQs() {
        return faqRepository.findAll().stream().map(faq -> faqMapper.FAQEntitytoBean(faq)).collect(Collectors.toList());
    }

    @Override
    public FAQBean getFAQ(long id) {
        return faqMapper.FAQEntitytoBean(faqRepository.getById(id));
    }

    @Override
    public FAQBean putFAQ(long id, String question, String answer) {
        FAQ faq = faqRepository.getById(id);
        faq.setQuestion(question);
        faq.setAnswer(answer);
        return faqMapper.FAQEntitytoBean(faqRepository.save(faq));
    }

    @Override
    public void deleteFAQ(long id) {
        faqRepository.deleteById(id);
    }


}
