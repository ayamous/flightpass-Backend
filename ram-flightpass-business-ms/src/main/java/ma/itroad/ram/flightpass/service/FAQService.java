package ma.itroad.ram.flightpass.service;


import ma.itroad.ram.flightpass.model.bean.FAQBean;

import java.util.List;

public interface FAQService {
   FAQBean postFAQ(String question ,  String answer);
   List<FAQBean> getFAQs();
   FAQBean getFAQ(long id );
   FAQBean putFAQ(long id ,String question , String answer);
   void deleteFAQ(long id);




}
