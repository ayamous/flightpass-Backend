package ma.itroad.ram.flightpass.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConfiguratioNotFound extends RuntimeException {
    public ConfiguratioNotFound(String message){
        super(message);
    }
}
