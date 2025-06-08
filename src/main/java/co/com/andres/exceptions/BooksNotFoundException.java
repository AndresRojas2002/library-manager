package co.com.andres.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No se encontro el libro con ese id")
public class BooksNotFoundException extends RuntimeException {

    public BooksNotFoundException(String message) {
        super(message);
    }

}
