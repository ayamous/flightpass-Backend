package ma.itroad.ram.flightpass.model.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAQBean {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id ;
    private String question;
    private String answer ;

}
