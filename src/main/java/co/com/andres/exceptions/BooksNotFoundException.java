package co.com.andres.exceptions;



//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No se encontro el libro con ese id")
public class BooksNotFoundException extends RuntimeException {

    public BooksNotFoundException() {
        super("NO SE ENCONTRO EL LIBRO CON ESE ID");
    }

}
