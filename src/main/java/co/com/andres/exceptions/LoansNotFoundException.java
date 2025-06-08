package co.com.andres.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No se encontro el libro con ese id")
public class LoansNotFoundException extends RuntimeException{
    public LoansNotFoundException(String message) {
        super(message);
    }
    
}
