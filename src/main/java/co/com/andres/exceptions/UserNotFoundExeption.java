package co.com.andres.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No se encontro el usuario con ese id")
public class UserNotFoundExeption extends RuntimeException{

    public UserNotFoundExeption(String message) {
        super(message);
    }
    
}
