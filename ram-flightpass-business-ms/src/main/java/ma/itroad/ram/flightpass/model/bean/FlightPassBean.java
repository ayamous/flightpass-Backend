package ma.itroad.ram.flightpass.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FlightPassBean {

    private String segment;
    private String currency;
    private double basePrice;
    private double baseTaxe;
    private double flightpassPrice;
    private LocalDate date;


    public FlightPassBean(String segment, String currency, Double flightpassPrice, LocalDate date) {
        this.segment = segment;
        this.currency = currency;
        this.flightpassPrice = flightpassPrice;
        this.date = date;
    }
}
