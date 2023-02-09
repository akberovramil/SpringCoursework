package akberov.ramil.coursework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NumberOfSocksException extends RuntimeException {

    @Override
    public String getMessage() {
        return ("Такого количества носков нет на складе");
    }
}
