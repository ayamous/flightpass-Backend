package ma.itroad.ram.flightpass.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasePriceRequestBean {

    private Long segmentId;
    private String currencyCode;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
