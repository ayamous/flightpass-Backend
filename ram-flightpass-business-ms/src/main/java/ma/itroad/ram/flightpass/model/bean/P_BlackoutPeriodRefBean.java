package ma.itroad.ram.flightpass.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class P_BlackoutPeriodRefBean implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String referenceBlackout;
    @NotNull(message = "you should provide Airport Id")
    private Long airportId;
    //
}

