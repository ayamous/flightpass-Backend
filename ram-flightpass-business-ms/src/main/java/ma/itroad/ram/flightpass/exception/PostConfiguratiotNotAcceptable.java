package ma.itroad.ram.flightpass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PostConfiguratiotNotAcceptable extends RuntimeException {

    public PostConfiguratiotNotAcceptable(String message){
        super(message);
    }
}
