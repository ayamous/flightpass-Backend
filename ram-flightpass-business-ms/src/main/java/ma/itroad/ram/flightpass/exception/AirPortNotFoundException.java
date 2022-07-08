package ma.itroad.ram.flightpass.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AirPortNotFoundException extends RuntimeException {
    public AirPortNotFoundException(String message){
        super(message);
    }
}
