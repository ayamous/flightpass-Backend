package ma.itroad.ram.flightpass.controller;


import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.FAQBean;
import ma.itroad.ram.flightpass.service.FAQService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/business")
public class FAQController {

    private FAQService faqService;


    @PostMapping(path="/api/faq")
    public ResponseEntity<FAQBean> postFAQ (@RequestBody FAQBean faqBean){
        return new ResponseEntity(faqService.postFAQ(faqBean.getQuestion() , faqBean.getAnswer()), HttpStatus.CREATED);
    }

    @GetMapping(path = "/api/faq")
    public ResponseEntity<FAQBean> getFAQs (){
        return new ResponseEntity(faqService.getFAQs() , HttpStatus.OK );
    }


    @GetMapping(path = "/api/faq/{id}")
    public ResponseEntity<FAQBean> getFAQ (@PathVariable long id ){
            FAQBean faqBean =  faqService.getFAQ(id);
            return new ResponseEntity( faqBean, HttpStatus.OK);
    }

    @PutMapping(path = "/api/faq/{id}")
    public ResponseEntity<FAQBean> putFAQ (@PathVariable long id ,@RequestBody  FAQBean faqBean){
        return new ResponseEntity(faqService.putFAQ(id , faqBean.getQuestion(),faqBean.getAnswer()),HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/faq/{id}")
    public  ResponseEntity<HttpStatus> deleteFAQ(@PathVariable long id){
        faqService.deleteFAQ(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
