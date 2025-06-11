package co.com.andres.exceptions;



//@ResponseStatus(value = HttpStatus.CONFLICT, reason = "El libro con ese ISBN ya existe")
public class BookWinthIsbnExistExeption extends RuntimeException {

    public BookWinthIsbnExistExeption() {
        super("EL LIBRO CON ESE ISBN YA EXISTE");
    }
}
