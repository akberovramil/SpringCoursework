package akberov.ramil.coursework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SocksNotFoundException extends RuntimeException {


    @Override
    public String getMessage() {
        return "Таких носков нет на складе";
    }
}
